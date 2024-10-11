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

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import org.springframework.util.StringUtils;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import co.oneplat.teamkeeper.jpa.converter.CodeLikeConverter;
import co.oneplat.teamkeeper.jpa.entity.base.AbstractUsableEntity;
import co.oneplat.teamkeeper.jpa.entity.user.constant.EmploymentState;

/**
 * 사용자
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "USER")
public class User extends AbstractUsableEntity {

    /**
     * 사용자 아이디
     */
    @Id
    @Column(name = "USER_ID", nullable = false, updatable = false)
    private String id;

    /**
     * 이름
     */
    @Column(name = "FIRST_NAME", nullable = false)
    private String firstName;

    /**
     * 성
     */
    @Column(name = "LAST_NAME", nullable = false)
    private String lastName;

    /**
     * 생년월일
     */
    @Column(name = "BIRTH_DATE", nullable = false)
    private LocalDate birthDate;

    /**
     * 입사일자
     */
    @Column(name = "JOIN_DATE", nullable = false)
    private LocalDate joinedDate;

    /**
     * 퇴사일자
     */
    @Column(name = "RESIGN_DATE")
    private LocalDate resignedDate;

    /**
     * 고용 상태
     */
    @Convert(converter = CodeLikeConverter.class)
    @Column(name = "EMP_STATE", nullable = false)
    private EmploymentState employmentState;

    // -------------------------------------------------------------------------------------------------

    @Builder
    @SuppressWarnings("unused")
    User(
            String id,
            String firstName,
            String lastName,
            LocalDate birthDate,
            LocalDate joinedDate,
            EmploymentState employmentState
    ) {
        if (!StringUtils.hasText(id)) {
            throw new IllegalArgumentException("User.id cannot be empty");
        }
        if (!StringUtils.hasText(firstName)) {
            throw new IllegalArgumentException("User.firstName cannot be empty");
        }
        if (!StringUtils.hasText(lastName)) {
            throw new IllegalArgumentException("User.lastName cannot be empty");
        }
        if (birthDate == null) {
            throw new IllegalArgumentException("User.birthDate cannot be null");
        }
        if (joinedDate == null) {
            throw new IllegalArgumentException("User.joinedDate cannot be null");
        }
        if (birthDate.isAfter(joinedDate)) {
            throw new IllegalArgumentException("User.joinedDate cannot be after User.birthDate");
        }
        if (employmentState == null) {
            throw new IllegalArgumentException("User.employmentState cannot be null");
        }

        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.joinedDate = joinedDate;
        this.employmentState = employmentState;
    }

    public boolean isResigned() {
        return this.employmentState == EmploymentState.RESIGNED
                && this.resignedDate != null
                && !this.resignedDate.isBefore(LocalDate.now());
    }

    public void resign(LocalDate resignedDate) {
        if (resignedDate == null) {
            throw new NullPointerException("resignedDate is null");
        }
        if (this.joinedDate.isAfter(resignedDate)) {
            throw new IllegalArgumentException("User.joinedDate cannot be after User.resignedDate");
        }

        this.resignedDate = resignedDate;
        this.employmentState = EmploymentState.RESIGNED;

        // 퇴사한 사용자를 비활성화한다.
        setUsed(false);
    }

}
