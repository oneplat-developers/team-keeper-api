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

package co.oneplat.teamkeeper.jpa.entity.user.approval;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import co.oneplat.teamkeeper.jpa.converter.CodeLikeConverter;
import co.oneplat.teamkeeper.jpa.entity.base.AbstractAuditableEntity;
import co.oneplat.teamkeeper.jpa.entity.user.User;
import co.oneplat.teamkeeper.jpa.entity.user.approval.constant.UserApprovalState;

/**
 * 사용자 승인
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "USER_APPROVAL")
public class UserApproval extends AbstractAuditableEntity {

    /**
     * 사용자
     */
    @Id
    @OneToOne(optional = false)
    @JoinColumn(name = "USER_ID")
    private User user;

    /**
     * 승인 상태
     */
    @Convert(converter = CodeLikeConverter.class)
    @Column(name = "APPROVE_STATE", nullable = false)
    private UserApprovalState state;

    // -------------------------------------------------------------------------------------------------

    @Builder
    @SuppressWarnings("unused")
    UserApproval(User user) {
        if (user == null) {
            throw new IllegalArgumentException("UserApproval.user cannot be null");
        }

        this.user = user;
        this.state = UserApprovalState.UNAUTHENTICATED;
    }

    public void authenticate() {
        this.state = UserApprovalState.AUTHENTICATED;
    }

    public void approve() {
        this.state = UserApprovalState.ADMINISTRATOR_APPROVED;
    }

    public void reject() {
        this.state = UserApprovalState.ADMINISTRATOR_REJECTED;
    }

}
