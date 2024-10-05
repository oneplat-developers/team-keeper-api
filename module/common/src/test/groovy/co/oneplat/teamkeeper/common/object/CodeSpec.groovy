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
        code                                | expected
        null                                | false
        ""                                  | false
        " "                                 | false
        "Alpha1"                            | false
        "GAMMA"                             | false
        "_foo"                              | false
        "bar_"                              | false
        "foo__bar"                          | false
        "lorem_ipsum/:/simply_dummy/:/text" | false
        "edit:comment_on_content/author"    | false
        "100_beta"                          | true
        "alpha"                             | true
        "omega1"                            | true
        "beta_gamma10"                      | true
        "a_b_c_0_1_2_3"                     | true
        "alpha_beta_gamma"                  | true
        "feed_animal:dog"                   | true
        "edit:comment_on_content:author"    | true
    }

    def "Parses value by delimiter and returns as fragments"() {
        given:
        def code = new Code(value)

        expect:
        code.fragments == expected

        where:
        value                         | expected
        "foo"                         | ["foo"]
        "foo_bar"                     | ["foo_bar"]
        "foo:bar"                     | ["foo", "bar"]
        "alpha_beta/gamma_delta/zeta" | ["alpha_beta", "gamma_delta", "zeta"]
        "01.02.03.04.05"              | ["01", "02", "03", "04", "05"]
    }

    def "Converts to JSON as its value"() {
        given:
        def jsonMapper = JsonMapper.builder().build()

        when:
        def json = jsonMapper.writeValueAsString(value)

        then:
        json == "\"$value\""

        where:
        value << ["alpha", "foo_bar", "beta:100_gamma", "z_e_t_a_0_1_2"]
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
        value << ["alpha", "foo_bar", "beta:100_gamma", "z_e_t_a_0_1_2"]
    }

}
