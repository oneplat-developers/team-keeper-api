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

package co.oneplat.teamkeeper.common.configuration.redis.embedded;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @param enabled 내장 레디스 활성화 여부
 * @param daemonize 데몬 프로세스 실행 여부
 * @param appendOnly AOF 여부
 * @param maxMemory 최대 메모리 허용량
 */
@ConfigurationProperties(prefix = "spring.data.redis.embedded")
public record EmbeddedRedisProperties(
        boolean enabled,
        boolean daemonize,
        boolean appendOnly,
        MaxMemory maxMemory
) {

    public EmbeddedRedisProperties {
        if (maxMemory == null) {
            maxMemory = new MaxMemory(128, Unit.MB);
        }
    }

    // -------------------------------------------------------------------------------------------------

    /**
     * @param capacity 허용량
     * @param unit 단위
     */
    public record MaxMemory(Integer capacity, Unit unit) {
        public MaxMemory {
            if (capacity == null) {
                throw new IllegalArgumentException("MaxMemory.capacity cannot be null.");
            }
            if (capacity <= 0) {
                throw new IllegalArgumentException("MaxMemory.capacity must be positive.");
            }
            if (unit == null) {
                throw new IllegalArgumentException("MaxMemory.unit cannot be null.");
            }
        }
    }

    @Getter
    @RequiredArgsConstructor
    public enum Unit {
        BYTE(""),
        KB("K"),
        MB("M"),
        GB("G");

        private final String value;
    }

}
