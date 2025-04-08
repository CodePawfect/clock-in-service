package github.codepawfect.clockinservice.domain.user.ports.in;

import org.springframework.http.ResponseCookie;

/** LoginUseCasePort is the input port for the login use case. It defines the contract for */
public interface LoginUseCasePort {

  /**
   * Logs in a user with the given username and password.
   *
   * @param username the username of the user
   * @param password the password of the user
   * @return a ResponseCookie containing the JWT token
   */
  ResponseCookie login(String username, String password);
}
