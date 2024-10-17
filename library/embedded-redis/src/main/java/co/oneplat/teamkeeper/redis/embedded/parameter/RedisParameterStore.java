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

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class RedisParameterStore {

    private final Map<String, List<RedisParameter>> paramClassNameMap;

    public RedisParameterStore() {
        this.paramClassNameMap = new ConcurrentHashMap<>();
    }

    // -------------------------------------------------------------------------------------------------

    public boolean has(Class<? extends RedisParameter> parameterType) {
        String className = parameterType.getName();
        return this.paramClassNameMap.containsKey(className);
    }

    public void add(RedisParameter parameter) {
        String className = parameter.getClass().getName();
        this.paramClassNameMap.compute(className, (k, v) -> {
            if (v == null) {
                List<RedisParameter> parameters = new CopyOnWriteArrayList<>();
                parameters.add(parameter);
                return parameters;
            }

            v.add(parameter);
            return v;
        });
    }

    public void set(RedisParameter parameter) {
        String className = parameter.getClass().getName();
        List<RedisParameter> parameters = new CopyOnWriteArrayList<>();
        parameters.add(parameter);

        this.paramClassNameMap.put(className, parameters);
    }

    public void clear() {
        this.paramClassNameMap.clear();
    }

    public <T extends RedisParameter> List<T> getParameters(Class<T> parameterType) {
        String className = parameterType.getName();

        @SuppressWarnings("unchecked")
        List<T> parameters = (List<T>) this.paramClassNameMap.getOrDefault(className, Collections.emptyList());

        return List.copyOf(parameters);
    }

}
