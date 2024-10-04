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

package co.oneplat.teamkeeper.common.object;

import java.util.regex.Pattern;

import jakarta.annotation.Nullable;

import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonValue;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import co.oneplat.teamkeeper.common.exception.Business;
import co.oneplat.teamkeeper.common.exception.BusinessException;

@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public final class Code {

    private static final Pattern CODE_PATTERN = Pattern.compile("^[a-z][a-z0-9]*(_[a-z0-9]+)*$");

    @JsonValue
    @EqualsAndHashCode.Include
    private final String value;

    public Code(@Nullable String value) {
        if (!isValid(value)) {
            throw new BusinessException(Business.PARSE_CODE_OBJECT);
        }

        this.value = value;
    }

    // -------------------------------------------------------------------------------------------------

    public static boolean isValid(@Nullable String value) {
        if (!StringUtils.hasText(value)) {
            return false;
        }

        return CODE_PATTERN.matcher(value).matches();
    }

}
