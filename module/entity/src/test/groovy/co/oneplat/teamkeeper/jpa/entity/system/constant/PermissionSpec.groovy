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

import com.fasterxml.jackson.databind.json.JsonMapper

class PermissionSpec extends Specification {

    def "Validates integrity of all permissions"() {
        when:
        def permissions = Permission.values()

        then: "Permission must be defined at least one"
        permissions.length > 0

        then: "All permissions have valid properties"
        permissions.length > 0
        permissions.every { it.code }
        permissions.every { !it.name.blank }

        then: "There is no duplicated permission"
        permissions.collect { it.code }.toSet().size() == permissions.size()
    }

    def "Converts to JSON as object which has its properties"() {
        given:
        def jsonMapper = JsonMapper.builder().build()

        when:
        def json = jsonMapper.writeValueAsString(permission)

        then:
        def expected = """
        {
            "code": #{code},
            "name": #{name}
        }
        """.replaceAll(~/\s/, '').replaceAll(~/#\{(\w+)}/) { fullMatch, key ->
            def property = permission[key as String]
            property ? jsonMapper.writeValueAsString(property) : fullMatch
        }
        json == expected

        where:
        permission << List.of(Permission.values())
    }

}
