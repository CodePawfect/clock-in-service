package github.codepawfect.clockinservice.adapter.in.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/** OpenApiConfig is a configuration class for OpenAPI. */
@Configuration
public class OpenApiConfig {

  /**
   * Custom OpenAPI configuration.
   *
   * @return the OpenAPI configuration
   */
  @Bean
  public OpenAPI customOpenAPI() {
    return new OpenAPI()
        .info(
            new Info()
                .title("Work Time API")
                .version("1.0")
                .description("API for managing work time entries")
                .contact(new Contact().name("Development Team").email("codepawfect@gmail.com")));
  }
}
