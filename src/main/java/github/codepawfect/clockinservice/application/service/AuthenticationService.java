package github.codepawfect.clockinservice.application.service;

import github.codepawfect.clockinservice.adapter.auth.exception.UserAlreadyExistsException;
import github.codepawfect.clockinservice.adapter.common.JwtUtils;
import github.codepawfect.clockinservice.domain.user.model.NewUser;
import github.codepawfect.clockinservice.domain.user.ports.in.CreateUserUseCasePort;
import java.util.Collections;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/** AuthenticationService serves as workflow coordinator related to authentication */
@Service
public class AuthenticationService {

  private final AuthenticationManager authenticationManager;
  private final JwtUtils jwtUtils;
  private final PasswordEncoder passwordEncoder;
  private final CreateUserUseCasePort createUserUseCasePort;

  public AuthenticationService(
      AuthenticationManager authenticationManager,
      JwtUtils jwtUtils,
      PasswordEncoder passwordEncoder,
      CreateUserUseCasePort createUserUseCasePort) {
    this.authenticationManager = authenticationManager;
    this.jwtUtils = jwtUtils;
    this.passwordEncoder = passwordEncoder;
    this.createUserUseCasePort = createUserUseCasePort;
  }

  /**
   * Authenticates a user with the given username and password.
   *
   * @param username the username
   * @param password the password
   * @return the authenticated user information
   */
  public ResponseCookie login(String username, String password) {
    Authentication authentication =
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(username, password));

    SecurityContextHolder.getContext().setAuthentication(authentication);

    UserDetails userDetails = (UserDetails) authentication.getPrincipal();

    return jwtUtils.generateJwtCookie(userDetails);
  }

  /**
   * Registers a new user with the given username, password, and email.
   *
   * @param username the username
   * @param password the password
   */
  public void register(String username, String password) throws UserAlreadyExistsException {
    NewUser newUser =
        new NewUser(username, passwordEncoder.encode(password), Collections.singletonList("USER"));

    createUserUseCasePort.createUser(newUser);
  }

  /**
   * Logs out the currently authenticated user.
   *
   * @return the cookie to delete
   */
  public ResponseCookie logout() {
    return jwtUtils.invalidateAuthCookie();
  }
}
