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

package co.oneplat.teamkeeper.redis.embedded.parameter;

import java.util.Collections;
import java.util.List;

public class SaveParameter implements RedisParameter {

    private final Integer seconds;

    private final Integer changes;

    public SaveParameter() {
        this.seconds = null;
        this.changes = null;
    }

    public SaveParameter(int seconds, int changes) {
        this.seconds = seconds;
        this.changes = changes;
    }

    @Override
    public Object getValue() {
        if (this.seconds == null || this.changes == null) {
            return "";
        }

        return String.format("%d %d", this.seconds, this.changes);
    }

    @Override
    public List<String> toArguments() {
        if (this.seconds == null || this.changes == null) {
            return Collections.emptyList();
        }

        return List.of("--save", String.format("%d %d", this.seconds, this.changes));
    }

}
