package github.codepawfect.clockinservice.domain.user.ports.in;

import org.springframework.http.ResponseCookie;

/** LogoutUseCasePort is the input port for the logout use case. */
public interface LogoutUseCasePort {

  /**
   * Logs out the user by invalidating the authentication cookie.
   *
   * @return a ResponseCookie indicating the logout status
   */
  ResponseCookie logout();
}
