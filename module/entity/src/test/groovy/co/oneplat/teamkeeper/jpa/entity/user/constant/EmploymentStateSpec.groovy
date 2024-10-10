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

package co.oneplat.teamkeeper.jpa.entity.user.constant

import spock.lang.Specification

import com.fasterxml.jackson.databind.json.JsonMapper

import co.oneplat.teamkeeper.common.object.Code

class EmploymentStateSpec extends Specification {

    def "Validates integrity of all employment states"() {
        when:
        def states = EmploymentState.values()

        then: "Employment state must be defined at least one"
        states.length > 0

        then: "All employment states have valid properties"
        states.length > 0
        states.every { it.code }
        states.every { !it.name.blank }

        then: "There is no duplicated employment state"
        states.collect { it.code }.toSet().size() == states.size()
    }

    def "Converts to JSON as object which has its properties"() {
        given:
        def jsonMapper = JsonMapper.builder().build()

        when:
        def json = jsonMapper.writeValueAsString(state)

        then:
        def expected = """
        {
            "code": #{code},
            "name": #{name}
        }
        """.replaceAll(~/\s/, '').replaceAll(~/#\{(\w+)}/) { fullMatch, key ->
            def property = state[key as String]
            property ? jsonMapper.writeValueAsString(property) : fullMatch
        }
        json == expected

        where:
        state << List.of(EmploymentState.values())
    }

    def "Returns the employment state matched by code"() {
        given:
        def code = new Code(codeValue)

        when:
        def state = EmploymentState.of(code).orElse(null)

        then:
        state == expected

        where:
        codeValue      | expected
        "leave"        | null
        "state:active" | null
        "active"       | EmploymentState.ACTIVE
        "on_leave"     | EmploymentState.ON_LEAVE
        "suspended"    | EmploymentState.SUSPENDED
        "resigned"     | EmploymentState.RESIGNED
    }

}
