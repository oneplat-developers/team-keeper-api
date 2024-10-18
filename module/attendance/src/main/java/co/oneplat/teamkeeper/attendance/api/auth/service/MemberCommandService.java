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

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import co.oneplat.teamkeeper.attendance.api.auth.dto.response.GoogleAccountProfileResponse;
import co.oneplat.teamkeeper.jpa.entity.user.User;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberCommandService {

    public User findOrCreateMemberBy(final GoogleAccountProfileResponse profile) {
        // return memberRepository.findByGoogleId(profile.id())
        //         .orElseGet(() -> userRe.save(
        //                 User.builder()
        //                         .name(profile.name())
        //                         .googleId(profile.id())
        //                         .email(profile.email())
        //                         .imageUrl(profile.picture())
        //                         .build()));
        return null;
    }

}
