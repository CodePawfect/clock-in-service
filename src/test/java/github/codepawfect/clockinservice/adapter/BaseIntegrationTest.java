package github.codepawfect.clockinservice.adapter;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

/**
 * BaseIntegrationTest is a base class for integration tests. It sets up the test environment,
 * including a MongoDB container and RestAssured port configuration.
 */
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
public class BaseIntegrationTest {
  @LocalServerPort private int port;

  /** MongoDB container for integration tests. It uses the latest MongoDB image from Docker Hub. */
  @Container @ServiceConnection
  public static MongoDBContainer mongoDbContainer = new MongoDBContainer("mongo:latest");

  /**
   * Sets up the RestAssured port before each test. This allows RestAssured to send requests to the
   * correct port.
   */
  @BeforeEach
  void setUp() {
    RestAssured.port = port;
  }
}
