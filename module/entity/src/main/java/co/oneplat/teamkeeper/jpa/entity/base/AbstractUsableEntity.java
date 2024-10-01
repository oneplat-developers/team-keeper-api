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

package co.oneplat.teamkeeper.jpa.entity.base;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.MappedSuperclass;

import org.hibernate.type.YesNoConverter;

import lombok.Getter;
import lombok.Setter;

import co.oneplat.teamkeeper.jpa.entity.Usable;

@Getter
@Setter
@MappedSuperclass
public abstract class AbstractUsableEntity extends AbstractAuditableEntity implements Usable {

    /**
     * 사용 여부
     */
    @Convert(converter = YesNoConverter.class)
    @Column(name = "USED", nullable = false)
    private boolean used;

    // -------------------------------------------------------------------------------------------------

    /**
     * 엔터티를 생성하는 경우, 명시적으로 설정하지 않으면 사용하는 것으로 간주한다.
     */
    protected AbstractUsableEntity() {
        this.used = true;
    }

}
