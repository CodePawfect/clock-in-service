package github.codepawfect.clockinservice;

import github.codepawfect.clockinservice.shared.JwtProperties;
import io.mongock.runner.springboot.EnableMongock;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(JwtProperties.class)
@EnableMongock
public class ClockInServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(ClockInServiceApplication.class, args);
  }
}
