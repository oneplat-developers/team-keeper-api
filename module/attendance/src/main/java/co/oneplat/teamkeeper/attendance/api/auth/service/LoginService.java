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

package co.oneplat.teamkeeper.attendance.api.auth.service;

import jakarta.servlet.http.HttpServletResponse;

import co.oneplat.teamkeeper.attendance.api.auth.component.GoogleClient;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

import co.oneplat.teamkeeper.attendance.api.auth.dto.request.GoogleLoginRequest;
import co.oneplat.teamkeeper.attendance.api.auth.dto.response.GoogleAccountProfileResponse;
import co.oneplat.teamkeeper.attendance.api.auth.dto.response.LoginResponse;
import co.oneplat.teamkeeper.attendance.configuration.JwtTokenGenerator;
import co.oneplat.teamkeeper.jpa.entity.user.User;

@Service
@AllArgsConstructor
public class LoginService {
    private final MemberCommandService memberCommandService;
    private final GoogleClient googleClient;
    private final JwtTokenGenerator jwtTokenGenerator;
    private final HttpServletResponse response;

    public LoginResponse loginByGoogle(final GoogleLoginRequest request) {
        final GoogleAccountProfileResponse profile = googleClient.getGoogleAccountProfile(request.code(),
                request.redirectUri());
        final User user = memberCommandService.findOrCreateMemberBy(profile);
        final String token = jwtTokenGenerator.generateToken(String.valueOf(user.getId()));
        return new LoginResponse(user.getId(), token);
    }
}

