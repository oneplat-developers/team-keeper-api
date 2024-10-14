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

package co.oneplat.teamkeeper.tester.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.TestPropertySource;

/**
 * Annotation that supports to configure spring context for test cases.
 * <p>
 * {@code @TestPropertySource(properties = "spring.profiles.active=test")} is can be replaced with
 * {@code @ActiveProfiles("test")}, but you cannot get the given value on the annotation for {@code spring.profiles.active}.
 * You will get the value through {@link Environment#getActiveProfiles()}, but not through {@link Environment#getProperty(String)}.
 * <p>
 * To make consistent test environment, uses {@code @TestPropertySource} instead of {@code @ActiveProfiles}.
 *
 * @see ActiveProfiles
 * @see TestPropertySource
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@TestPropertySource(properties = "spring.profiles.active=test")
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public @interface SpringTestContextConfigure {
}
