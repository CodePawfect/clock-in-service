package github.codepawfect.clockinservice.application.in.auth.usecase;

import org.springframework.http.ResponseCookie;

public interface LogoutUserUseCase {

  /**
   * Logs out the user by clearing the session cookie.
   *
   * @return a response containing the cleared session cookie
   */
  LogoutUserResponseDTO execute();

  record LogoutUserResponseDTO(ResponseCookie cookie) {}
}
