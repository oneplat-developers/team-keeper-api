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

package co.oneplat.teamkeeper.common.exception

import spock.lang.Specification

import com.fasterxml.jackson.databind.json.JsonMapper

class BusinessSpec extends Specification {

    def "Validates integrity of all businesses"() {
        when:
        def businesses = Business.values()

        then: "Businesses must be defined at least one"
        businesses.length > 0

        then: "All businesses have valid properties"
        businesses.length > 0
        businesses.every { it.code }
        businesses.every { it.domain }
        businesses.every { !it.errorMessage.blank }

        then: "There is no duplicated business"
        businesses.collect { it.code }.toSet().size() == businesses.size()
    }

    def "Converts to JSON as object which has its properties"() {
        given:
        def jsonMapper = JsonMapper.builder().build()

        when:
        def json = jsonMapper.writeValueAsString(business)

        then:
        def expected = """
        {
            "code": #{code},
            "domain": #{domain},
            "errorMessage": #{errorMessage}
        }
        """.replaceAll(~/\s/, '').replaceAll(~/#\{(\w+)}/) { fullMatch, key ->
            def property = business[key as String]
            property ? jsonMapper.writeValueAsString(property) : fullMatch
        }
        json == expected

        where:
        business << List.of(Business.values())
    }

}
