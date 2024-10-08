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

package co.oneplat.teamkeeper.jpa.entity.system.constant

import spock.lang.Specification

import org.springframework.http.HttpMethod

import co.oneplat.teamkeeper.common.object.Code

class PermissionSpec extends Specification {

    def "All permissions have valid properties"() {
        given:
        def permissions = Permission.values()

        expect:
        permissions.length > 0
        permissions.every { Code.isValid(it.code.value, ':' as char) }
        permissions.every { !it.name.blank }
        permissions.every {
            [HttpMethod.GET, HttpMethod.POST, HttpMethod.PUT, HttpMethod.DELETE].contains(it.apiMethod)
        }
    }

}
