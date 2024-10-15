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

import co.oneplat.teamkeeper.jpa.entity.user.approval.constant.UserApprovalState

class UserApprovalStateConverterSpec extends Specification {

    def "Converts to database column as its code"() {
        given:
        def converter = new UserApprovalStateConverter()

        when:
        def converted = converter.convertToDatabaseColumn(state)

        then:
        def expected = state?.code?.value
        converted == expected

        where:
        state << [null] + UserApprovalState.values().toList()
    }

    def "Converts to entity attribute as its code"() {
        given:
        def converter = new UserApprovalStateConverter()

        when:
        def converted = converter.convertToEntityAttribute(persistent)

        then:
        converted == expected

        where:
        persistent               | expected
        null                     | null
        ""                       | null
        "UNAUTHENTICATED"        | null
        "AUTHENTICATED"          | null
        "ADMINISTRATOR_APPROVED" | null
        "ADMINISTRATOR_REJECTED" | null
        "unauthenticated"        | UserApprovalState.UNAUTHENTICATED
        "authenticated"          | UserApprovalState.AUTHENTICATED
        "admin_approved"         | UserApprovalState.ADMINISTRATOR_APPROVED
        "admin_rejected"         | UserApprovalState.ADMINISTRATOR_REJECTED
    }

}
