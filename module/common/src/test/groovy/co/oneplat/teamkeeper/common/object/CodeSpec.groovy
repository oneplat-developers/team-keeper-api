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

package co.oneplat.teamkeeper.common.object

import spock.lang.Specification

import com.fasterxml.jackson.databind.json.JsonMapper

class CodeSpec extends Specification {

    def "Checks if it is valid as code"() {
        when:
        def valid = Code.isValid(code)

        then:
        valid == expected

        where:
        code               || expected
        null               || false
        ""                 || false
        " "                || false
        "Alpha1"           || false
        "100_beta"         || false
        "GAMMA"            || false
        "foo__bar"         || false
        "feed:dog"         || false
        "alpha"            || true
        "omega1"           || true
        "beta_gamma10"     || true
        "alpha_beta_gamma" || true
    }

    def "Converts to JSON as its value"() {
        given:
        def jsonMapper = JsonMapper.builder().build()

        when:
        def json = jsonMapper.writeValueAsString(value)

        then:
        json == "\"$value\""

        where:
        value << ["alpha", "foo_bar", "beta_100_gamma", "z_e_t_a_0_1_2"]
    }

    def "Converts to object as its value"() {
        given:
        def jsonMapper = JsonMapper.builder().build()
        def json = "\"$value\""

        when:
        def code = jsonMapper.readValue(json, Code)

        then:
        code == new Code(value)

        where:
        value << ["alpha", "foo_bar", "beta_100_gamma", "z_e_t_a_0_1_2"]
    }

}
