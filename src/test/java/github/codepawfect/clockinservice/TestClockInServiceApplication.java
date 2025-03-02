package github.codepawfect.clockinservice;

import org.springframework.boot.SpringApplication;

public class TestClockInServiceApplication {

    public static void main(String[] args) {
        SpringApplication.from(ClockInServiceApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
