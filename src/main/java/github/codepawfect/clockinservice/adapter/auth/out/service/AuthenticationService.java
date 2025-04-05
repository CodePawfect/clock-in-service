package github.codepawfect.clockinservice.adapter.auth.out.service;

import github.codepawfect.clockinservice.adapter.auth.out.model.UserDocument;
import github.codepawfect.clockinservice.adapter.auth.out.repository.UserRepository;
import github.codepawfect.clockinservice.adapter.auth.out.service.exception.UserAlreadyExistsException;
import github.codepawfect.clockinservice.adapter.auth.out.service.model.AuthenticatedUserInformation;
import github.codepawfect.clockinservice.adapter.common.JwtUtils;
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
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/** AuthenticationService is a service class that handles authentication and logout operations. */
@Service
public class AuthenticationService {

  private final AuthenticationManager authenticationManager;
  private final JwtUtils jwtUtils;
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public AuthenticationService(
      AuthenticationManager authenticationManager,
      JwtUtils jwtUtils,
      UserRepository userRepository,
      PasswordEncoder passwordEncoder) {
    this.authenticationManager = authenticationManager;
    this.jwtUtils = jwtUtils;
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
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
   * @throws UserAlreadyExistsException if a user with the given username or email already exists
   */
  public void register(String username, String password) throws UserAlreadyExistsException {
    if (userRepository.existsByUsername(username)) {
      throw new UserAlreadyExistsException("Username is already taken");
    }

    UserDocument user =
        new UserDocument(
            null, username, passwordEncoder.encode(password), Collections.singletonList("USER"));

    userRepository.save(user);
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

    UserDocument userDocument =
        userRepository
            .findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));

    return new AuthenticatedUserInformation(
        userDocument.username(), userDocument.roles(), jwtUtils.generateJwtCookie(userDocument));
  }
}
