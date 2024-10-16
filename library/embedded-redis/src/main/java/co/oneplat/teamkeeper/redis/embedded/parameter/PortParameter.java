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

import java.io.IOException;
import java.net.ServerSocket;
import java.util.List;

public class PortParameter implements RedisParameter {

    private final int port;

    public PortParameter(int port) {
        this.port = port == 0 ? findRandomAvailablePort() : port;
    }

    @Override
    public Object getValue() {
        return this.port;
    }

    @Override
    public List<String> toArguments() {
        return List.of("--port", String.valueOf(this.port));
    }

    // -------------------------------------------------------------------------------------------------

    private int findRandomAvailablePort() {
        // Creates an unbound socket to get a free port.
        try (ServerSocket socket = new ServerSocket(0)) {
            // Returns the port that was allocated
            return socket.getLocalPort();
        } catch (IOException ignored) {
            // Returns the unbound port.
            return -1;
        }
    }

}
