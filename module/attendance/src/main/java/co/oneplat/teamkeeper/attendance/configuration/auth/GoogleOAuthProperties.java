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

package co.oneplat.teamkeeper.attendance.configuration.auth;

import java.util.Set;

import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @see OAuth2ClientProperties
 */
@Getter
@ConfigurationProperties(prefix = "spring.security.oauth2.client.google")
public class GoogleOAuthProperties {

    private final String clientId;

    private final String clientSecret;

    private final String clientAuthenticationMethod;

    private final String authorizationGrantType;

    private final String redirectUri;

    private final Set<String> scope;

    private final String clientName;

    private final Url url;

    @ConstructorBinding
    public GoogleOAuthProperties(
            String clientId,
            String clientSecret,
            String clientAuthenticationMethod,
            String authorizationGrantType,
            String redirectUri,
            Set<String> scope,
            String clientName,
            Url url
    ) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.clientAuthenticationMethod = clientAuthenticationMethod;
        this.authorizationGrantType = authorizationGrantType;
        this.redirectUri = redirectUri;
        this.scope = Set.copyOf(scope);
        this.clientName = clientName;
        this.url = url;
    }

    // -------------------------------------------------------------------------------------------------

    @Getter
    @RequiredArgsConstructor
    public static class Url {

        private final String accessToken;

        private final String profile;

    }

}
