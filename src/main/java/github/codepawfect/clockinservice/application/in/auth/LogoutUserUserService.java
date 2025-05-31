package github.codepawfect.clockinservice.application.in.auth;

import github.codepawfect.clockinservice.application.in.auth.usecase.LogoutUserUseCase;
import github.codepawfect.clockinservice.shared.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogoutUserUserService implements LogoutUserUseCase {

  private final JwtUtils jwtUtils;

  /** {@inheritDoc} */
  @Override
  public LogoutUserResponseDTO execute() {
    return new LogoutUserResponseDTO(jwtUtils.invalidateAuthCookie());
  }
}
