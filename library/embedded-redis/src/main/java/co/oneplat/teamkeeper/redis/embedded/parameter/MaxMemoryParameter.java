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

package co.oneplat.teamkeeper.redis.embedded.parameter;

import java.util.List;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MaxMemoryParameter implements RedisParameter {

    private final int maxMemory;

    private final char unit;

    @Override
    public Object getValue() {
        return String.format("%d%s", this.maxMemory, this.unit);
    }

    @Override
    public List<String> toArguments() {
        return List.of("--maxmemory", String.format("%d%s", this.maxMemory, this.unit));
    }

}
