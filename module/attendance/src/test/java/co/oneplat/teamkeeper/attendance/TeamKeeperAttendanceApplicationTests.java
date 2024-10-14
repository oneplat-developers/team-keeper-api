package co.oneplat.teamkeeper.attendance;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestConstructor;

import lombok.RequiredArgsConstructor;

import co.oneplat.teamkeeper.tester.annotation.SpringTestContextConfigure;

@SpringBootTest
@SpringTestContextConfigure
// @ActiveProfiles("test")
@RequiredArgsConstructor
// @TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class TeamKeeperAttendanceApplicationTests {

    @Value("${spring.datasource.hikari.driver-class-name}")
    private final Class<?> driverClass;
    // @Value("${spring.profiles.active}")
    // private final String activeProfile;

    private final Environment environment;

    @Test
    void contextLoads() {
        System.out.println(driverClass);
        // System.out.println(activeProfile);
        System.out.println(Arrays.toString(environment.getActiveProfiles()));
        System.out.println(Arrays.toString(environment.getDefaultProfiles()));
        System.out.println(environment.getProperty("spring.profiles.active"));
    }

}
