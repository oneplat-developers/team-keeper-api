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

package co.oneplat.teamkeeper.jpa.entity.system;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.util.StringUtils;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import co.oneplat.teamkeeper.common.exception.Business;
import co.oneplat.teamkeeper.common.exception.BusinessException;
import co.oneplat.teamkeeper.common.object.Code;
import co.oneplat.teamkeeper.jpa.converter.CodeConverter;
import co.oneplat.teamkeeper.jpa.entity.base.AbstractAuditableEntity;

/**
 * 공통 그룹 코드
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "COMMON_GROUP_CODE")
public class CommonGroupCode extends AbstractAuditableEntity {

    /**
     * 그룹코드
     */
    @Id
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Convert(converter = CodeConverter.class)
    @Column(name = "GROUP_ID", nullable = false, updatable = false)
    private Code id;

    /**
     * 그룹코드 이름
     */
    @Column(name = "GROUP_NAME", nullable = false)
    private String name;

    /**
     * 정렬 번호
     */
    @Column(name = "SORT_NO", nullable = false, unique = true)
    private Integer sortNumber;

    // -------------------------------------------------------------------------------------------------

    @Builder
    @SuppressWarnings("unused")
    CommonGroupCode(String id, String name, Integer sortNumber) {
        if (!Code.isValid(id)) {
            throw new BusinessException(Business.CREATE_COMMON_GROUP_CODE, "올바른 형식의 그룹코드가 아닙니다.");
        }
        if (!StringUtils.hasText(name)) {
            throw new BusinessException(Business.CREATE_COMMON_GROUP_CODE, "그룹코드 이름에 값이 없습니다.");
        }
        if (sortNumber == null) {
            throw new BusinessException(Business.CREATE_COMMON_GROUP_CODE, "정렬번호에 값이 없습니다.");
        }
        if (sortNumber < 1) {
            throw new BusinessException(Business.CREATE_COMMON_GROUP_CODE, "정렬번호는 1 이상이어야 합니다.");
        }

        this.id = new Code(id);
        this.name = name;
        this.sortNumber = sortNumber;
    }

}
