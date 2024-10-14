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

import jakarta.persistence.EntityManager

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.context.SpringBootTest

import lombok.RequiredArgsConstructor

import co.oneplat.teamkeeper.common.object.Code
import co.oneplat.teamkeeper.jpa.entity.test.TesterEntity
import co.oneplat.teamkeeper.jpa.entity.user.constant.EmploymentState
import co.oneplat.teamkeeper.tester.annotation.SpringTestContextConfigure

@DataJpaTest
//@SpringBootTest
//@RequiredArgsConstructor
@SpringTestContextConfigure
class ConvertSpec extends Specification {

    @Autowired
    private EntityManager entityManager

    def "test"() {
        given:
        def entity = new TesterEntity();
        entity.id = 100
        entity.code = new Code("foo_bar")
        entity.employmentState = EmploymentState.ON_LEAVE

        when:
        entityManager.persist(entity)
        entityManager.flush()
        entityManager.detach(entity)

        then:
        def foundEntity = entityManager.find(TesterEntity, entity.id)
        foundEntity != null
        foundEntity.id == entity.id
        foundEntity.code == entity.code
        foundEntity.employmentState == entity.employmentState
    }

}
