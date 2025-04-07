package github.codepawfect.clockinservice.adapter;

import github.codepawfect.clockinservice.adapter.auth.out.model.UserDocument;
import github.codepawfect.clockinservice.adapter.auth.out.repository.UserRepository;
import github.codepawfect.clockinservice.adapter.common.JwtUtils;
import io.restassured.RestAssured;
import io.restassured.http.Cookie;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.userdetails.UserDetails;
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
public abstract class BaseIntegrationTest {
  @Autowired protected JwtUtils jwtUtils;
  @Autowired protected UserRepository userRepository;
  @LocalServerPort private int port;

  private UserDetails testUserDetails;

  /** MongoDB container for integration tests. It uses the latest MongoDB image from Docker Hub. */
  @Container @ServiceConnection
  public static MongoDBContainer mongoDbContainer = new MongoDBContainer("mongo:latest");

  /** Sets up the RestAssured port before each test. */
  @BeforeEach
  void setUp() {
    RestAssured.port = port;

    testUserDetails =
        new UserDocument("testUserId", "testUser", "encodedPassword", List.of("USER"));
    userRepository.save((UserDocument) testUserDetails);
  }

  /** Cleans up the database after each test. */
  @AfterEach
  void cleanUp() {
    userRepository.deleteAll();
  }

  /**
   * Generates a RestAssured Cookie object containing the JWT for the given UserDetails.
   *
   * @param userDetails The UserDetails for whom to generate the auth cookie.
   * @return A RestAssured Cookie object.
   */
  protected Cookie getAuthToken(UserDetails userDetails) {
    ResponseCookie springCookie = jwtUtils.generateJwtCookie(userDetails);

    return new Cookie.Builder(springCookie.getName(), springCookie.getValue()).build();
  }

  /**
   * Generates a RestAssured Cookie object containing the JWT for the default test user.
   *
   * @return A RestAssured Cookie object.
   */
  protected Cookie getDefaultUserAuthToken() {
    ResponseCookie springCookie = jwtUtils.generateJwtCookie(testUserDetails);

    return new Cookie.Builder(springCookie.getName(), springCookie.getValue()).build();
  }
}
