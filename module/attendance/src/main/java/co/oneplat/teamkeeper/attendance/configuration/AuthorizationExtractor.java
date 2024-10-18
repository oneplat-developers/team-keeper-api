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

package co.oneplat.teamkeeper.attendance.configuration;


import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;

import co.oneplat.teamkeeper.attendance.api.auth.exception.LoginException;

import static co.oneplat.teamkeeper.attendance.api.auth.exception.LoginExceptionType.*;

public class AuthorizationExtractor {
    private static final String ACCESS_TOKEN_TYPE = AuthorizationExtractor.class.getSimpleName() + ".ACCESS_TOKEN_TYPE";
    private static final String BEARER_TYPE = "Bearer";

    public static String extract(final HttpServletRequest request) {
        final String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        validateAuthorizationHeader(authorizationHeader);

        String authHeaderValue = authorizationHeader.substring(BEARER_TYPE.length()).trim();

        request.setAttribute(ACCESS_TOKEN_TYPE, BEARER_TYPE);
        final int commaIndex = authHeaderValue.indexOf(',');
        if (commaIndex > 0) {
            authHeaderValue = authHeaderValue.substring(0, commaIndex);
        }
        return authHeaderValue;
    }

    private static void validateAuthorizationHeader(final String authorizationHeader) {
        if (authorizationHeader == null || authorizationHeader.isBlank()) {
            throw new LoginException(NOT_FOUND_AUTHORIZATION_TOKEN);
        }
        if (!authorizationHeader.toLowerCase().startsWith(BEARER_TYPE.toLowerCase())) {
            throw new LoginException(INVALID_ACCESS_TOKEN);
        }
    }
}
