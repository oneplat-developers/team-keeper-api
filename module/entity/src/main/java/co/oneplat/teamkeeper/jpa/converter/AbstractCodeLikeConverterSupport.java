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

package co.oneplat.teamkeeper.jpa.converter;

import jakarta.persistence.AttributeConverter;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import co.oneplat.teamkeeper.common.object.Code;
import co.oneplat.teamkeeper.common.object.CodeLike;

abstract class AbstractCodeLikeConverterSupport<T extends CodeLike>
        implements AttributeConverter<T, String> {

    @Override
    public final String convertToDatabaseColumn(T attribute) {
        if (attribute == null) {
            return null;
        }

        return attribute.getCode().getValue();
    }

    @Override
    public final T convertToEntityAttribute(String dbData) {
        if (dbData == null || !Code.isValid(dbData)) {
            return null;
        }

        return convertCodeLikeToEntityAttribute(new Code(dbData));
    }

    @Nullable
    protected abstract T convertCodeLikeToEntityAttribute(@NotNull Code code);

}
