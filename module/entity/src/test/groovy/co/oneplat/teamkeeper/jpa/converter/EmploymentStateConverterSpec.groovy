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

package co.oneplat.teamkeeper.jpa.converter

import spock.lang.Specification

import co.oneplat.teamkeeper.jpa.entity.user.constant.EmploymentState

class EmploymentStateConverterSpec extends Specification {

    def "Converts to database column as its code"() {
        given:
        def converter = new EmploymentStateConverter()

        when:
        def converted = converter.convertToDatabaseColumn(state)

        then:
        def expected = state?.code?.value
        converted == expected

        where:
        state << [null] + EmploymentState.values().toList()
    }

    def "Converts to entity attribute as its code"() {
        given:
        def converter = new EmploymentStateConverter()

        when:
        def converted = converter.convertToEntityAttribute(persistent)

        then:
        converted == expected

        where:
        persistent  | expected
        null        | null
        ""          | null
        "ACTIVE"    | null
        "ON_LEAVE"  | null
        "SUSPENDED" | null
        "RESIGNED"  | null
        "active"    | EmploymentState.ACTIVE
        "on_leave"  | EmploymentState.ON_LEAVE
        "suspended" | EmploymentState.SUSPENDED
        "resigned"  | EmploymentState.RESIGNED
    }

}
