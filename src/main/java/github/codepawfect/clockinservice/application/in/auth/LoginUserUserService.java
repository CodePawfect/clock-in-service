package github.codepawfect.clockinservice.application.in.auth;

import github.codepawfect.clockinservice.application.in.auth.usecase.LoginUserUseCase;
import github.codepawfect.clockinservice.shared.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginUserUserService implements LoginUserUseCase {

  private final AuthenticationManager authenticationManager;
  private final JwtUtils jwtUtils;

  /** {@inheritDoc} */
  @Override
  public LoginUserResponseDTO execute(LoginUserQueryDTO query) {
    Authentication authentication =
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(query.username(), query.password()));

    SecurityContextHolder.getContext().setAuthentication(authentication);

    UserDetails userDetails = (UserDetails) authentication.getPrincipal();

    return new LoginUserResponseDTO(jwtUtils.generateJwtCookie(userDetails));
  }
}
