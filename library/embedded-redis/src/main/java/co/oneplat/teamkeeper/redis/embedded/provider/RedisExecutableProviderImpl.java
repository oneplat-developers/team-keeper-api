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

package co.oneplat.teamkeeper.redis.embedded.provider;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import redis.embedded.util.JarUtil;

import co.oneplat.teamkeeper.redis.embedded.Platform;

public class RedisExecutableProviderImpl implements RedisExecutableProvider {

    @Override
    public Path getExecutablePath() {
        String fileName = resolveFileName();
        Path filePath = Path.of(fileName);

        if (Files.exists(filePath)) {
            return filePath;
        }

        try {
            return JarUtil.extractExecutableFromJar(fileName).toPath();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // -------------------------------------------------------------------------------------------------

    private static String resolveFileName() {
        return switch (Platform.getCurrentPlatform()) {
            case AIX, SOLARIS, LINUX -> "binaries/redis-server-7.4.1-linux-x64";
            case MACOS_ARM64 -> "binaries/redis-server-7.4.1-mac-arm64";
            case MACOS_X64 -> "binaries/redis-server-7.4.1-mac-x64";
            case WINDOWS -> "binaries/redis-server-5.0.14.1-win-x64.exe";
            case UNKNOWN -> "";
        };
    }

}
