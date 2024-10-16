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

package co.oneplat.teamkeeper.redis.embedded;

import java.util.List;
import java.util.regex.Pattern;

import lombok.extern.slf4j.Slf4j;
import redis.embedded.RedisServer;

import co.oneplat.teamkeeper.redis.embedded.provider.RedisExecutableProvider;
import co.oneplat.teamkeeper.redis.embedded.provider.RedisExecutableProviderImpl;

/**
 * @see RedisServer
 */
@Slf4j
public class EmbeddedRedisServer extends AbstractRedisInstance {

    private static final Pattern REDIS_READY_PATTERN = Pattern.compile(
            ".*Ready to accept connections.*", Pattern.CASE_INSENSITIVE);

    public static final int DEFAULT_PORT = 6379;

    public static final int RANDOM_PORT = 0;

    public EmbeddedRedisServer(int port) {
        this(port, new RedisExecutableProviderImpl());
    }

    public EmbeddedRedisServer(int port, RedisExecutableProvider provider) {
        super(port, List.of(
                provider.getExecutablePath().toAbsolutePath().toString(),
                "--port", String.valueOf(port)
        ));
    }

    EmbeddedRedisServer(int port, List<String> args) {
        super(port, args);
    }

    public static EmbeddedRedisServerBuilder builder() {
        return new EmbeddedRedisServerBuilder();
    }

    @Override
    protected Pattern redisReadyPattern() {
        return REDIS_READY_PATTERN;
    }

}
