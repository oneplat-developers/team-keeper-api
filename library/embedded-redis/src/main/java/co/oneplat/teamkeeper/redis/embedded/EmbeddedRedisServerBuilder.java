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

import redis.embedded.RedisServerBuilder;

import co.oneplat.teamkeeper.redis.embedded.provider.RedisExecutableProvider;
import co.oneplat.teamkeeper.redis.embedded.provider.RedisExecutableProviderImpl;

/**
 * @see RedisServerBuilder
 */
public class EmbeddedRedisServerBuilder {

    private RedisExecutableProvider provider;

    private String bind;

    private int port = -1;

    private boolean daemonize;

    private boolean appendOnly;

    private int maxMemory;

    private List<String> modulePaths;

    // Building ----------------------------------------------------------------------------------------

    public EmbeddedRedisServerBuilder provider(RedisExecutableProvider provider) {
        this.provider = provider;
        return this;
    }

    public EmbeddedRedisServerBuilder bind(String bind) {
        this.bind = bind;
        return this;
    }

    public EmbeddedRedisServerBuilder port(int port) {
        this.port = port;
        return this;
    }

    public EmbeddedRedisServerBuilder daemonize(boolean daemonize) {
        this.daemonize = daemonize;
        return this;
    }

    public EmbeddedRedisServerBuilder appendOnly(boolean appendOnly) {
        this.appendOnly = appendOnly;
        return this;
    }

    public EmbeddedRedisServerBuilder maxMemory(int maxMemory) {
        this.maxMemory = maxMemory;
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

    // ---------- --------------------------------------------------------------------------------------

    public EmbeddedRedisServer build() {
        if (this.provider == null) {
            provider(new RedisExecutableProviderImpl());
        }

        if (this.bind == null) {
            bind("127.0.0.1");
        }

        if (this.port == -1) {
            port(EmbeddedRedisServer.DEFAULT_PORT);
        }

        if (this.maxMemory == 0) {
            maxMemory(128);
        }

        if (this.modulePaths == null) {
            loadModules(Collections.emptyList());
        }

        List<String> args = new ArrayList<>();
        args.add(this.provider.getExecutablePath().toAbsolutePath().toString());
        args.addAll(List.of("--bind", this.bind));
        args.addAll(List.of("--port", String.valueOf(this.port)));
        args.addAll(List.of("--daemonize", this.daemonize ? "yes" : "no"));
        args.addAll(List.of("--appendonly", this.appendOnly ? "yes" : "no"));
        args.addAll(List.of("--maxmemory", this.maxMemory + "M"));
        if (this.modulePaths != null && !this.modulePaths.isEmpty()) {
            args.addAll(List.of("--loadmodule", String.join(" ", this.modulePaths)));
        }

        return new EmbeddedRedisServer(this.port, List.copyOf(args));
    }

}
