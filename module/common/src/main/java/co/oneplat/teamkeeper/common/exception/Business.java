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

package co.oneplat.teamkeeper.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public enum Business {

    // Object ------------------------------------------------------------------------------------------

    PARSE_CODE_OBJECT("common", "parse:code_object", "올바른 형식의 코드가 아닙니다."),

    // Common Group Code -------------------------------------------------------------------------------

    CREATE_COMMON_GROUP_CODE("common_group_code", "create:common_group_code", "공통 그룹 코드를 생성하는 데에 실패했습니다."),

    // -------------------------------------------------------------------------------------------------

    ;

    private final String domain;

    private final String code;

    private final String errorMessage;

}
