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

import co.oneplat.teamkeeper.common.object.Code

class CodeConverterSpec extends Specification {

    def "Converts to database column as its value"() {
        given:
        def converter = new CodeConverter()

        when:
        def converted = converter.convertToDatabaseColumn(code)

        then:
        converted == expected

        where:
        code                    | expected
        null                    | null
        new Code("foo_bar")     | code.value
        new Code("alpha:beta")  | code.value
        new Code("123/456/789") | code.value
    }

    def "Converts to entity attribute as its value"() {
        given:
        def converter = new CodeConverter()

        when:
        def converted = converter.convertToEntityAttribute(persistent)

        then:
        converted == expected

        where:
        persistent    | expected
        null          | null
        ""            | null
        "FOO_BAR"     | null
        "foo_bar"     | new Code(persistent)
        "alpha:beta"  | new Code(persistent)
        "123/456/789" | new Code(persistent)
    }

}
