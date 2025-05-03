package github.codepawfect.clockinservice.application.driven.auth;

import github.codepawfect.clockinservice.shared.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoginUseCase {

  private final AuthenticationManager authenticationManager;
  private final JwtUtils jwtUtils;

  /**
   * Logs in a user with the given username and password.
   *
   * @param username the username of the user
   * @param password the password of the user
   * @return a ResponseCookie containing the JWT token
   */
  public ResponseCookie execute(String username, String password) {
    Authentication authentication =
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(username, password));

    SecurityContextHolder.getContext().setAuthentication(authentication);

    UserDetails userDetails = (UserDetails) authentication.getPrincipal();

    return jwtUtils.generateJwtCookie(userDetails);
  }
}
