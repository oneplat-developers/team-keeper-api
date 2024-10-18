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

package co.oneplat.teamkeeper.attendance.api.auth.controller;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import co.oneplat.teamkeeper.attendance.api.auth.service.LoginService;
import co.oneplat.teamkeeper.attendance.api.auth.dto.request.GoogleLoginRequest;
import co.oneplat.teamkeeper.attendance.api.auth.dto.response.LoginResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final LoginService loginService;

    // POST /login
    @PostMapping("/login/email")
    public ResponseEntity<LoginResponse> loginByGoogle(@Valid @RequestBody Map<String, String> request) {
        System.out.println("--------------------" + request);
        return ResponseEntity.ok(null);
    }

    // POST /login
    @PostMapping("/login/social")
    public ResponseEntity<LoginResponse> loginByGoogle(@Valid @RequestBody final GoogleLoginRequest request) throws
            UnsupportedEncodingException {
        System.out.println("--------------------" + request);
        return ResponseEntity.ok(loginService.loginByGoogle(request));
    }

    // POST /logout

}
