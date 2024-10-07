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

import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jakarta.annotation.Nullable;

import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonValue;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import co.oneplat.teamkeeper.common.exception.Business;
import co.oneplat.teamkeeper.common.exception.BusinessException;

@Getter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public final class Code {

    private static final Pattern FRAGMENT_PATTERN = Pattern.compile("[a-z0-9]+(?:_[a-z0-9]+)*");

    @JsonValue
    @EqualsAndHashCode.Include
    private final String value;

    // -------------------------------------------------------------------------------------------------

    public Code(@Nullable String value) {
        if (!isValid(value)) {
            throw new BusinessException(Business.PARSE_CODE_OBJECT, String.format("올바른 형식의 코드가 아닙니다: '%s'", value));
        }

        this.value = value;
    }

    // -------------------------------------------------------------------------------------------------

    public static boolean isValid(@Nullable String value) {
        return isValid(value, Character.MIN_VALUE);
    }

    public static boolean isValid(@Nullable String value, char delimiter) {
        if (!StringUtils.hasText(value)) {
            return false;
        }

        Matcher matcher = FRAGMENT_PATTERN.matcher(value);

        int fragmentCount = -1;
        int totalFragmentsLength = 0;
        char previousDelimiter = delimiter;

        while (matcher.find()) {
            fragmentCount++;

            String fragment = matcher.group();
            totalFragmentsLength += fragment.length();

            int previousMatchedIndex = matcher.start();
            if (previousMatchedIndex > 0) {
                char delim = value.charAt(previousMatchedIndex - 1);

                if (previousDelimiter != Character.MIN_VALUE && previousDelimiter != delim) {
                    return false;
                }

                previousDelimiter = delim;
            }
        }

        return value.length() == fragmentCount + totalFragmentsLength;
    }

    public List<String> getFragments() {
        return FRAGMENT_PATTERN.matcher(this.value).results().map(MatchResult::group).toList();
    }

}
