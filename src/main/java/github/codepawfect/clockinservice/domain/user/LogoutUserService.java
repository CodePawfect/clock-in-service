package github.codepawfect.clockinservice.domain.user;

import github.codepawfect.clockinservice.application.in.auth.LogoutUseCase;
import github.codepawfect.clockinservice.shared.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogoutUserService implements LogoutUseCase {

  private final JwtUtils jwtUtils;

  /** {@inheritDoc} */
  @Override
  public ResponseCookie execute() {
    return jwtUtils.invalidateAuthCookie();
  }
}
