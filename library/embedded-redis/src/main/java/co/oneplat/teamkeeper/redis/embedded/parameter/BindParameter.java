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

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

import redis.embedded.exceptions.EmbeddedRedisException;

public class BindParameter implements RedisParameter {

    private final InetAddress host;

    public BindParameter(String host) {
        try {
            this.host = InetAddress.getByName(host);
        } catch (UnknownHostException e) {
            throw new EmbeddedRedisException(e.getMessage(), e);
        }
    }

    @Override
    public Object getValue() {
        return this.host;
    }

    @Override
    public List<String> toArguments() {
        return List.of("--bind", this.host.getHostName());
    }

}
