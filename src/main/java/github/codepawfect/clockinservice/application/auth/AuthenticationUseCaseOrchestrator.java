package github.codepawfect.clockinservice.application.auth;

import github.codepawfect.clockinservice.adapter.auth.exception.UserAlreadyExistsException;
import github.codepawfect.clockinservice.adapter.common.JwtUtils;
import github.codepawfect.clockinservice.domain.user.model.NewUser;
import github.codepawfect.clockinservice.domain.user.ports.in.LoginUseCasePort;
import github.codepawfect.clockinservice.domain.user.ports.in.LogoutUseCasePort;
import github.codepawfect.clockinservice.domain.user.ports.in.RegisterUseCasePort;
import github.codepawfect.clockinservice.domain.user.service.UserService;
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
public class AuthenticationUseCaseOrchestrator
    implements LoginUseCasePort, LogoutUseCasePort, RegisterUseCasePort {

  private final AuthenticationManager authenticationManager;
  private final JwtUtils jwtUtils;
  private final PasswordEncoder passwordEncoder;
  private final UserService userService;

  public AuthenticationUseCaseOrchestrator(
      AuthenticationManager authenticationManager,
      JwtUtils jwtUtils,
      PasswordEncoder passwordEncoder,
      UserService userService) {
    this.authenticationManager = authenticationManager;
    this.jwtUtils = jwtUtils;
    this.passwordEncoder = passwordEncoder;
    this.userService = userService;
  }

  /** {@inheritDoc} */
  @Override
  public ResponseCookie login(String username, String password) {
    Authentication authentication =
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(username, password));

    SecurityContextHolder.getContext().setAuthentication(authentication);

    UserDetails userDetails = (UserDetails) authentication.getPrincipal();

    return jwtUtils.generateJwtCookie(userDetails);
  }

  /** {@inheritDoc} */
  @Override
  public void register(String username, String password) throws UserAlreadyExistsException {
    NewUser newUser =
        new NewUser(username, passwordEncoder.encode(password), Collections.singletonList("USER"));

    userService.createUser(newUser);
  }

  /** {@inheritDoc} */
  @Override
  public ResponseCookie logout() {
    return jwtUtils.invalidateAuthCookie();
  }
}
