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

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import co.oneplat.teamkeeper.jpa.entity.Deletable;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@MappedSuperclass
public abstract class AbstractDeletableEntity extends AbstractAuditableEntity implements Deletable {

    /**
     * 삭제 여부
     */
    @Convert(converter = YesNoConverter.class)
    @Column(name = "DELETED", nullable = false)
    private boolean deleted;

    // -------------------------------------------------------------------------------------------------

    @Override
    public void delete() {
        this.deleted = true;
    }

}
