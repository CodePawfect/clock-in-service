package github.codepawfect.clockinservice.application.service;

import github.codepawfect.clockinservice.adapter.auth.exception.UserAlreadyExistsException;
import github.codepawfect.clockinservice.adapter.auth.model.AuthenticatedUserInformation;
import github.codepawfect.clockinservice.adapter.auth.model.CustomUserDetails;
import github.codepawfect.clockinservice.adapter.common.JwtUtils;
import github.codepawfect.clockinservice.domain.user.model.NewUser;
import github.codepawfect.clockinservice.domain.user.model.User;
import github.codepawfect.clockinservice.domain.user.ports.in.CreateUserUseCasePort;
import github.codepawfect.clockinservice.domain.user.ports.in.GetUserUseCasePort;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
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
  private final GetUserUseCasePort getUserUseCasePort;
  private final CreateUserUseCasePort createUserUseCasePort;

  public AuthenticationService(
      AuthenticationManager authenticationManager,
      JwtUtils jwtUtils,
      PasswordEncoder passwordEncoder,
      GetUserUseCasePort getUserUseCasePort,
      CreateUserUseCasePort createUserUseCasePort) {
    this.authenticationManager = authenticationManager;
    this.jwtUtils = jwtUtils;
    this.passwordEncoder = passwordEncoder;
    this.getUserUseCasePort = getUserUseCasePort;
    this.createUserUseCasePort = createUserUseCasePort;
  }

  /**
   * Authenticates a user with the given username and password.
   *
   * @param username the username
   * @param password the password
   * @return the authenticated user information
   */
  public AuthenticatedUserInformation login(String username, String password) {
    Authentication authentication =
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(username, password));

    SecurityContextHolder.getContext().setAuthentication(authentication);

    UserDetails userDetails = (UserDetails) authentication.getPrincipal();

    // TODO: roles should be part of the jwt payload -add them to the jwt-
    ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);

    List<String> roles =
        userDetails.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.toList());

    return new AuthenticatedUserInformation(userDetails.getUsername(), roles, jwtCookie);
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

  /**
   * Authenticates a user with the given token. Refreshes the JWT cookie if the token is valid.
   *
   * @param token the authentication token
   * @return the authenticated user information
   */
  public AuthenticatedUserInformation authenticate(String token) {
    String username = jwtUtils.getUsernameByToken(token);
    User user = getUserUseCasePort.getUserByUsername(username);

    UserDetails userDetails = new CustomUserDetails(user);

    return new AuthenticatedUserInformation(
        user.username(), user.roles(), jwtUtils.generateJwtCookie(userDetails));
  }
}
