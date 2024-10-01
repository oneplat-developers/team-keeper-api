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

package co.oneplat.teamkeeper.jpa.entity.base;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.util.StringUtils;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractAuditableEntity {

    /**
     * 생성자 아이디
     */
    @CreatedBy
    @Column(name = "CREATED_USER_ID", nullable = false, updatable = false)
    private String createdUserId;

    /**
     * 생성일시
     */
    @CreatedDate
    @Column(name = "CREATED_AT", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /**
     * 수정자 아이디
     */
    @LastModifiedBy
    @Column(name = "LAST_MODIFIED_USER_ID", nullable = false)
    private String lastModifiedUserId;

    /**
     * 수정일시
     */
    @LastModifiedDate
    @Column(name = "LAST_MODIFIED_AT", nullable = false)
    private LocalDateTime lastModifiedAt;

    // -------------------------------------------------------------------------------------------------

    public boolean isCreated() {
        if (this.createdAt == null || this.lastModifiedAt == null) {
            return false;
        }

        return StringUtils.hasText(this.createdUserId) && StringUtils.hasText(this.lastModifiedUserId);
    }

    public boolean isModified() {
        if (!isCreated()) {
            return false;
        }

        return !this.createdUserId.equals(this.lastModifiedUserId) || this.createdAt.isBefore(this.lastModifiedAt);
    }

}
