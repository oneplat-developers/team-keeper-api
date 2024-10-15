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

package co.oneplat.teamkeeper.jpa.entity.test;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import lombok.Getter;
import lombok.Setter;

import co.oneplat.teamkeeper.common.object.Code;
import co.oneplat.teamkeeper.jpa.converter.CodeConverter;
import co.oneplat.teamkeeper.jpa.converter.EmploymentStateConverter;
import co.oneplat.teamkeeper.jpa.entity.user.constant.EmploymentState;

@Getter
@Setter
@Entity
public class TesterEntity {

    @Id
    @Column(name = "ID")
    private Long id;

    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Convert(converter = CodeConverter.class)
    @Column(name = "CODE")
    private Code code;

    @Convert(converter = EmploymentStateConverter.class)
    @Column(name = "EMP_STATE")
    private EmploymentState employmentState;

}
