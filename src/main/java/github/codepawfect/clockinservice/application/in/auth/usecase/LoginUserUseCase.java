package github.codepawfect.clockinservice.application.in.auth.usecase;

import org.springframework.http.ResponseCookie;

public interface LoginUserUseCase {

  /**
   * Logs in a user with the provided credentials.
   *
   * @param query the login command containing username and password
   * @return a response containing a cookie if login is successful
   * @throws IllegalArgumentException if the username or password is null or blank
   */
  LoginUserResponseDTO execute(LoginUserQueryDTO query);

  record LoginUserResponseDTO(ResponseCookie cookie) {}

  record LoginUserQueryDTO(String username, String password) {
    public LoginUserQueryDTO {
      if (username == null || username.isBlank()) {
        throw new IllegalArgumentException("Username must not be null or blank");
      }
      if (password == null || password.isBlank()) {
        throw new IllegalArgumentException("Password must not be null or blank");
      }
    }
  }
}
