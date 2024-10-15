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
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import redis.embedded.Redis;
import redis.embedded.exceptions.EmbeddedRedisException;

import static java.util.stream.Collectors.*;

/**
 * See {@code redis.embedded.AbstractRedisInstance}
 */
@Slf4j
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class AbstractRedisInstance implements Redis {

    @Getter
    private volatile boolean active = false;

    private final int port;

    private final List<String> args;

    private Process redisProcess;

    private ExecutorService executor;

    // -------------------------------------------------------------------------------------------------

    @Override
    public synchronized void start() throws EmbeddedRedisException {
        if (this.active) {
            throw new EmbeddedRedisException("This redis server instance is already running...");
        }

        try {
            this.redisProcess = createRedisProcessBuilder().start();
            installExitHook();
            logErrorMessageIfRedisProcessFailed();
            awaitRedisServerReady();
            this.active = true;
        } catch (Exception e) {
            throw new EmbeddedRedisException("Failed to start Redis instance", e);
        }
    }

    private ProcessBuilder createRedisProcessBuilder() {
        File executable = new File(this.args.getFirst());
        return new ProcessBuilder(this.args)
                .directory(executable.getParentFile());
    }

    private void installExitHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(this::stop, "RedisInstanceCleaner"));
    }

    private void logErrorMessageIfRedisProcessFailed() {
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(this.redisProcess.getErrorStream(), StandardCharsets.UTF_8));
        Runnable printErrorTask = new PrintReaderRunnable(reader);

        this.executor = Executors.newSingleThreadExecutor();
        this.executor.submit(printErrorTask);
    }

    private void awaitRedisServerReady() throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(this.redisProcess.getInputStream()))) {
            StringBuilder sb = new StringBuilder();
            Pattern pattern = redisReadyPattern();

            String outputLine;
            do {
                outputLine = reader.readLine();

                // Something goes wrong. Stream is ended before server was activated.
                if (outputLine == null) {
                    String message = "Can't start redis server. Check logs for details. Redis process log: " + sb;
                    throw new RuntimeException(message);
                }

                sb.append(System.lineSeparator());
                sb.append(outputLine);
            } while (!pattern.matcher(outputLine).matches());
        }
    }

    protected abstract Pattern redisReadyPattern();

    // -------------------------------------------------------------------------------------------------

    @Override
    public synchronized void stop() throws EmbeddedRedisException {
        if (!this.active) {
            return;
        }

        if (log.isInfoEnabled()) {
            log.info("Stopping embedded redis server...");
        }

        if (this.executor != null && !this.executor.isShutdown()) {
            this.executor.shutdown();
            try {
                this.executor.awaitTermination(10, TimeUnit.SECONDS);
            } catch (InterruptedException ignored) {
            }
        }

        this.redisProcess.destroy();
        tryWaitFor();

        if (log.isInfoEnabled()) {
            log.info("Embedded redis exited.");
        }

        this.active = false;
    }

    private void tryWaitFor() {
        try {
            this.redisProcess.waitFor();
        } catch (InterruptedException e) {
            throw new EmbeddedRedisException("Failed to stop redis instance", e);
        }
    }

    @Override
    public List<Integer> ports() {
        return List.of(this.port);
    }

    // -------------------------------------------------------------------------------------------------

    private record PrintReaderRunnable(BufferedReader reader) implements Runnable {
        @Override
        public void run() {
            try {
                logMessagesByReader(this.reader);
            } catch (IOException e) {
                throw new EmbeddedRedisException("Failed to read error message", e);
            } finally {
                IOUtils.closeQuietly(this.reader);
            }
        }

        private static void logMessagesByReader(Reader reader) throws IOException {
            List<String> messages = IOUtils.readLines(reader);
            log.error(messages.stream().collect(joining(System.lineSeparator())));
        }
    }

}
