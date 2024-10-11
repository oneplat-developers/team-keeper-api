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

package co.oneplat.teamkeeper.jpa.entity.system.constant;

import java.util.Optional;
import java.util.stream.Stream;

import org.jetbrains.annotations.Nullable;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.ToString;

import co.oneplat.teamkeeper.common.object.Code;
import co.oneplat.teamkeeper.common.object.CodeLike;

@Getter
@ToString
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Permission implements CodeLike {

    // User --------------------------------------------------------------------------------------------

    APPROVE_USER_JOIN("approve:user_join", "회원가입 승인"),

    // Attendance --------------------------------------------------------------------------------------

    MODIFY_MY_OLD_ATTENDANCE("modify:my_old_attendance", "자신의 오래된 근태현황 수정"),
    CREATE_OTHER_ATTENDANCE("create:other_attendance", "다른 사용자의 근태현황 등록"),
    MODIFY_OTHER_ATTENDANCE("modify:other_attendance", "다른 사용자의 근태현황 수정"),
    REMOVE_OTHER_ATTENDANCE("remove:other_attendance", "다른 사용자의 근태현황 삭제"),

    ;

    // -------------------------------------------------------------------------------------------------

    Permission(String code, String name) {
        this.code = new Code(code);
        this.name = name;
    }

    private final Code code;

    private final String name;

    public static Optional<Permission> of(@Nullable Code code) {
        return Stream.of(values()).filter(it -> it.code.equals(code)).findFirst();
    }

}
