package github.codepawfect.clockinservice.application.in.auth;

import org.springframework.http.ResponseCookie;

public interface LoginUseCase {

  /**
   * Logs in a user with the given username and password.
   *
   * @param username the username of the user
   * @param password the password of the user
   * @return a ResponseCookie containing the JWT token
   */
  ResponseCookie execute(String username, String password);
}
