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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jetbrains.annotations.Nullable;

import redis.embedded.RedisServerBuilder;

import co.oneplat.teamkeeper.redis.embedded.parameter.AppendOnlyParameter;
import co.oneplat.teamkeeper.redis.embedded.parameter.BindParameter;
import co.oneplat.teamkeeper.redis.embedded.parameter.DaemonizeParameter;
import co.oneplat.teamkeeper.redis.embedded.parameter.ExecutableProvisionParameter;
import co.oneplat.teamkeeper.redis.embedded.parameter.MaxMemoryParameter;
import co.oneplat.teamkeeper.redis.embedded.parameter.PortParameter;
import co.oneplat.teamkeeper.redis.embedded.parameter.RedisParameter;
import co.oneplat.teamkeeper.redis.embedded.parameter.RedisParameterStore;
import co.oneplat.teamkeeper.redis.embedded.parameter.SaveParameter;
import co.oneplat.teamkeeper.redis.embedded.provider.RedisExecutableProvider;
import co.oneplat.teamkeeper.redis.embedded.provider.RedisExecutableProviderImpl;

import static java.util.function.Predicate.*;

/**
 * @see RedisServerBuilder
 */
public class EmbeddedRedisServerBuilder {

    private final RedisParameterStore parameterStore;

    public EmbeddedRedisServerBuilder() {
        this.parameterStore = new RedisParameterStore();
    }

    private List<String> modulePaths;

    // Parameters --------------------------------------------------------------------------------------

    public EmbeddedRedisServerBuilder provider(RedisExecutableProvider provider) {
        this.parameterStore.set(new ExecutableProvisionParameter(provider));
        return this;
    }

    public EmbeddedRedisServerBuilder bind(String bind) {
        this.parameterStore.set(new BindParameter(bind));
        return this;
    }

    public EmbeddedRedisServerBuilder port(int port) {
        this.parameterStore.set(new PortParameter(port));
        return this;
    }

    public EmbeddedRedisServerBuilder saves(@Nullable List<String> saves) {
        if (saves == null || saves.isEmpty()) {
            this.parameterStore.set(new SaveParameter());
        } else {
            saves.stream()
                    .filter(not(String::isBlank))
                    .map(String::strip)
                    .distinct()
                    .map(it -> {
                        String[] strings = it.split(" ");
                        return new SaveParameter(Integer.parseInt(strings[0]), Integer.parseInt(strings[1]));
                    })
                    .forEach(this.parameterStore::add);
        }

        return this;
    }

    public EmbeddedRedisServerBuilder daemonize(boolean daemonize) {
        this.parameterStore.set(new DaemonizeParameter(daemonize));
        return this;
    }

    public EmbeddedRedisServerBuilder appendOnly(boolean appendOnly) {
        this.parameterStore.set(new AppendOnlyParameter(appendOnly));
        return this;
    }

    public EmbeddedRedisServerBuilder maxMemory(int maxMemory, char unit) {
        this.parameterStore.set(new MaxMemoryParameter(maxMemory, unit));
        return this;
    }

    // public EmbeddedRedisServerBuilder slaveOf(String hostname, int port) {
    //     this.slaveOf = new InetSocketAddress(hostname, port);
    //     return this;
    // }
    //
    // public EmbeddedRedisServerBuilder slaveOf(InetSocketAddress slaveOf) {
    //     this.slaveOf = slaveOf;
    //     return this;
    // }

    public EmbeddedRedisServerBuilder loadModules(List<String> modulePaths) {
        this.modulePaths = modulePaths;
        return this;
    }

    // -------------------------------------------------------------------------------------------------

    public EmbeddedRedisServer build() {
        setupNotConfiguredParameters();

        List<Class<? extends RedisParameter>> parameterTypes = List.of(
                ExecutableProvisionParameter.class,
                BindParameter.class,
                PortParameter.class,
                SaveParameter.class,
                DaemonizeParameter.class,
                AppendOnlyParameter.class,
                MaxMemoryParameter.class
        );

        List<String> arguments = new ArrayList<>();
        for (Class<? extends RedisParameter> parameterType : parameterTypes) {
            List<? extends RedisParameter> parameters = this.parameterStore.getParameters(parameterType);
            parameters.forEach(it -> arguments.addAll(it.toArguments()));
        }

        PortParameter portParameter = this.parameterStore.getParameters(PortParameter.class).getFirst();
        int port = Integer.parseInt(String.valueOf(portParameter.getValue()));

        return new EmbeddedRedisServer(port, List.copyOf(arguments));
    }

    private void setupNotConfiguredParameters() {
        if (!this.parameterStore.has(ExecutableProvisionParameter.class)) {
            provider(new RedisExecutableProviderImpl());
        }

        if (this.modulePaths == null) {
            loadModules(Collections.emptyList());
        }
    }

}
