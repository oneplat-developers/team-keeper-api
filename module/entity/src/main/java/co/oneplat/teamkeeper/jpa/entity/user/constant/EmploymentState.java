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

package co.oneplat.teamkeeper.jpa.entity.user.constant;

import java.util.Optional;
import java.util.stream.Stream;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.ToString;

import co.oneplat.teamkeeper.common.object.Code;

@Getter
@ToString
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum EmploymentState {

    ACTIVE("active", "재직중"),
    ON_LEAVE("on_leave", "휴직중"),
    SUSPENDED("suspended", "정직"),
    RESIGNED("resigned", "퇴사");

    // -------------------------------------------------------------------------------------------------

    EmploymentState(String code, String name) {
        this.code = new Code(code);
        this.name = name;
    }

    private final Code code;

    private final String name;

    public static Optional<EmploymentState> of(Code code) {
        return Stream.of(values()).filter(it -> it.code.equals(code)).findFirst();
    }

}
