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

package co.oneplat.teamkeeper.jpa.entity.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import org.springframework.util.StringUtils;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import co.oneplat.teamkeeper.jpa.entity.base.AbstractUsableEntity;

/**
 * 소셜 사용자
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "SOCIAL_USER")
public class SocialUser extends AbstractUsableEntity {

    /**
     * 소셜 사용자 아이디
     */
    @Id
    @Column(name = "SOCIAL_ID", nullable = false, updatable = false)
    private String id;

    /**
     * 사용자
     */
    @OneToOne
    @JoinColumn(name = "USER_ID", unique = true, insertable = false)
    private User user;

    // -------------------------------------------------------------------------------------------------

    @Builder
    @SuppressWarnings("unused")
    SocialUser(String id) {
        if (!StringUtils.hasText(id)) {
            throw new IllegalArgumentException("SocialUser.id cannot be empty");
        }

        this.id = id;
    }

    public void connect(User user) {
        this.user = user;
    }

}
