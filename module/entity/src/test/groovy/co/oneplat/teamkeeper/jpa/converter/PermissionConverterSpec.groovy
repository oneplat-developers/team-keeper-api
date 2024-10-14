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

import co.oneplat.teamkeeper.jpa.entity.system.constant.Permission

class PermissionConverterSpec extends Specification {

    def "Converts to database column as its code"() {
        given:
        def converter = new PermissionConverter()

        when:
        def converted = converter.convertToDatabaseColumn(permission)

        then:
        def expected = permission?.code?.value
        converted == expected

        where:
        permission << [null] + Permission.values().toList()
    }

    def "Converts to entity attribute as its code"() {
        given:
        def converter = new PermissionConverter()

        when:
        def converted = converter.convertToEntityAttribute(persistent)

        then:
        converted == expected

        where:
        persistent                 | expected
        null                       | null
        ""                         | null
        "APPROVE_USER_JOIN"        | null
        "MODIFY_MY_OLD_ATTENDANCE" | null
        "CREATE_OTHER_ATTENDANCE"  | null
        "MODIFY_OTHER_ATTENDANCE"  | null
        "approve:user_join"        | Permission.APPROVE_USER_JOIN
        "modify:my_old_attendance" | Permission.MODIFY_MY_OLD_ATTENDANCE
        "create:other_attendance"  | Permission.CREATE_OTHER_ATTENDANCE
        "modify:other_attendance"  | Permission.MODIFY_OTHER_ATTENDANCE
    }

}
