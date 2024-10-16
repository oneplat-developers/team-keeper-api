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

package co.oneplat.teamkeeper.redis.embedded

import spock.lang.Ignore
import spock.lang.Specification

import io.lettuce.core.RedisClient

@Ignore("Replaced with 'com.github.codemonstur:embedded-redis'")
class EmbeddedRedisServerSpec extends Specification {

    def "Checks if server is bound to random port"() {
        given:
        def server = EmbeddedRedisServer.builder().port(0).build()

        when:
        server.start()

        then:
        isBoundPort(server.ports().first)

        cleanup:
        server.stop()
    }

    def "Checks if server accepts commands exactly from client"() {
        given: "Start server"
        def server = EmbeddedRedisServer.builder().port(0).build()
        server.start()

        and: "Create client"
        def port = server.ports().first
        def client = RedisClient.create("redis://localhost:$port")

        and: "Open a synchronous connection"
        def connection = client.connect()
        def syncCommands = connection.sync()

        when: "Send commands to server"
        def ttl = 500
        syncCommands.psetex("foo", ttl, "bar")
        def value = syncCommands.get("foo")

        then:
        value == "bar"

        when: "Wait to expire stored data"
        sleep(ttl)
        value = syncCommands.get("foo")

        then: "Store data is removed after time to live"
        value == null

        cleanup:
        connection.close()
        client.close()
        server.stop()
    }

    // -------------------------------------------------------------------------------------------------

    private static boolean isBoundPort(int port) {
        try (ServerSocket socket = new ServerSocket(port)) {
            false
        } catch (IOException ignored) {
            true
        }
    }

}
