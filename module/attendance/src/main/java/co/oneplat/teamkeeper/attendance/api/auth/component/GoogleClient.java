/*
 * Copyright 2024 OnePlat
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package co.oneplat.teamkeeper.attendance.api.auth.component;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.RequiredArgsConstructor;

import co.oneplat.teamkeeper.attendance.api.auth.dto.request.GoogleAccessTokenRequest;
import co.oneplat.teamkeeper.attendance.api.auth.dto.response.GoogleAccessTokenResponse;
import co.oneplat.teamkeeper.attendance.api.auth.dto.response.GoogleAccountProfileResponse;
import co.oneplat.teamkeeper.attendance.api.auth.exception.LoginException;
import co.oneplat.teamkeeper.attendance.configuration.auth.GoogleOAuthProperties;

import static co.oneplat.teamkeeper.attendance.api.auth.exception.LoginExceptionType.*;

@Component
@RequiredArgsConstructor
public class GoogleClient implements InitializingBean {

    private final GoogleOAuthProperties props;

    private final RestTemplate restTemplate;

    private WebClient webClient;

    public GoogleAccountProfileResponse getGoogleAccountProfile(final String code, final String redirectUri) {
        final String accessToken = requestGoogleAccessToken(code, redirectUri);
        return requestGoogleAccountProfile(accessToken);
    }

    private String requestGoogleAccessToken(final String code, final String redirectUri) {
        final String decodedCode = URLDecoder.decode(code, StandardCharsets.UTF_8);
        final HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        final HttpEntity<GoogleAccessTokenRequest> httpEntity = new HttpEntity<>(
                new GoogleAccessTokenRequest(decodedCode, props.getClientId()
                        , props.getClientSecret(), redirectUri,
                       props.getAuthorizationGrantType()),
                headers
        );

        final GoogleAccessTokenResponse response = restTemplate.exchange(
                props.getUrl().getAccessToken(), HttpMethod.POST, httpEntity, GoogleAccessTokenResponse.class
        ).getBody();
        return Optional.ofNullable(response)
                .orElseThrow(() -> new LoginException(NOT_FOUND_GOOGLE_ACCESS_TOKEN_RESPONSE))
                .accessToken();
    }

    private GoogleAccountProfileResponse requestGoogleAccountProfile(final String accessToken) {
        final HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        final HttpEntity<GoogleAccessTokenRequest> httpEntity = new HttpEntity<>(headers);
        return restTemplate.exchange(props.getUrl().getProfile(), HttpMethod.GET, httpEntity, GoogleAccountProfileResponse.class)
                .getBody();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.webClient = WebClient.builder()
                .baseUrl(props.getUrl().getAccessToken())
                .build();
    }

}
