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

import java.util.List;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisOperations;

import lombok.extern.slf4j.Slf4j;
import redis.embedded.Redis;

import co.oneplat.teamkeeper.redis.embedded.EmbeddedRedisServer;

@Slf4j
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass({RedisOperations.class, Redis.class})
@ConditionalOnProperty(prefix = "spring.data.redis.embedded", name = "enabled", havingValue = "true")
@AutoConfigureAfter(RedisAutoConfiguration.class)
@EnableConfigurationProperties(EmbeddedRedisProperties.class)
public class EmbeddedRedisAutoConfiguration {

    @Bean(initMethod = "start", destroyMethod = "stop")
    @ConditionalOnMissingBean(Redis.class)
    Redis embeddedRedisInstance(RedisProperties redisProperties) {
        log.debug("Starting embedded redis server");

        return EmbeddedRedisServer.builder()
                .port(redisProperties.getPort())
                .daemonize(false)
                .saves(List.of())
                .appendOnly(false)
                .maxMemory(256)
                .build();
    }

}
