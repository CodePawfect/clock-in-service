package github.codepawfect.clockinservice.application.in.auth;

import org.springframework.http.ResponseCookie;

public interface LogoutUseCase {

  /**
   * Logs out the user by invalidating the authentication cookie.
   *
   * @return a ResponseCookie indicating the logout status
   */
  ResponseCookie execute();
}
