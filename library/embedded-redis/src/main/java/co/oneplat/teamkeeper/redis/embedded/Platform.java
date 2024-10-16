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

package co.oneplat.teamkeeper.redis.embedded;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Locale;
import java.util.Objects;

public enum Platform {

    AIX {
        @Override
        public boolean isCurrent() {
            String osName = getCurrentOsName();
            return osName.contains("aix");
        }
    },

    SOLARIS {
        @Override
        public boolean isCurrent() {
            String osName = getCurrentOsName();
            return osName.contains("sunos") || osName.contains("solaris");
        }
    },

    LINUX {
        @Override
        public boolean isCurrent() {
            String osName = getCurrentOsName();
            return osName.contains("nix") || osName.contains("nux");
        }
    },

    MACOS_ARM64 {
        @Override
        public boolean isCurrent() {
            String osName = getCurrentOsName();
            if (!osName.contains("mac") && !osName.contains("darwin")) {
                return false;
            }

            String arch = getCurrentArchitecture();
            if (arch.contains("aarch64") || arch.contains("arm")) {
                return true;
            }

            try {
                Process process = Runtime.getRuntime().exec(new String[] {"sysctl", "sysctl.proc_translated"});
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                    // case translated: "sysctl.proc_translated: 1"
                    // case not translated: "sysctl.proc_translated: 0"
                    // case not arm64: "unknown old 'sysctl.proc_translated'"
                    String line = reader.readLine();
                    if (line != null && (line.endsWith("0") || line.endsWith("1"))) {
                        return true;
                    }
                }
            } catch (IOException ignored) {
            }

            return false;
        }
    },

    MACOS_X64 {
        @Override
        public boolean isCurrent() {
            String osName = getCurrentOsName();
            if (!osName.contains("mac") && !osName.contains("darwin")) {
                return false;
            }

            String arch = getCurrentArchitecture();
            return arch.contains("x86_x64");
        }
    },

    WINDOWS {
        @Override
        public boolean isCurrent() {
            String osName = getCurrentOsName();
            return osName.contains("win");
        }
    },

    UNKNOWN {
        @Override
        public boolean isCurrent() {
            return false;
        }
    };

    // -------------------------------------------------------------------------------------------------

    public static Platform getCurrentPlatform() {
        // THE ORDER OF EACH CONSTANT IS HIGHLY SENSITIVE TO RESOLUTION.
        for (Platform platform : values()) {
            if (platform.isCurrent()) {
                return platform;
            }
        }

        return UNKNOWN;
    }

    public abstract boolean isCurrent();

    private static String getCurrentOsName() {
        return Objects.requireNonNullElse(System.getProperty("os.name"), "").toLowerCase(Locale.US);
    }

    private static String getCurrentArchitecture() {
        return Objects.requireNonNullElse(System.getProperty("os.arch"), "").toLowerCase(Locale.US);
    }

}
