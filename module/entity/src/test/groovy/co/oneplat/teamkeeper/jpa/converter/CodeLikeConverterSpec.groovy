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

import jakarta.persistence.Column
import jakarta.persistence.Convert
import jakarta.persistence.Entity
import jakarta.persistence.EntityManager
import jakarta.persistence.Id

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.ImportAutoConfiguration
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.TestConstructor

import lombok.AccessLevel
import lombok.NoArgsConstructor

import co.oneplat.teamkeeper.common.object.Code

@DataJpaTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@AutoConfigureTestDatabase(
        replace = AutoConfigureTestDatabase.Replace.AUTO_CONFIGURED,
        connection = EmbeddedDatabaseConnection.H2
)
@ContextConfiguration(locations = "classpath:application-test.yml")
@ImportAutoConfiguration
class CodeLikeConverterSpec extends Specification {

    @Autowired
    private EntityManager entityManager

    def "Converts to database column"() {
        given:
        def entity = new TestEntity(100)

        when:
        entityManager.persist(entity)

        then:
        def foundEntity = entityManager.find(TestEntity, entity.id)
        foundEntity == entity
    }

    def "Converts to entity attribute"() {

    }

}

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
class TestEntity {

    @Id
    @Column(name = "ID")
    final Long id

    @Convert(converter = CodeLikeConverter)
    @Column(name = "CODE")
    Code code

    TestEntity(Long id) {
        this.id = id
    }

}
