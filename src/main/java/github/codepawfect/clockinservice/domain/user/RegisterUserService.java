package github.codepawfect.clockinservice.domain.user;

import github.codepawfect.clockinservice.application.in.auth.RegisterUseCase;
import github.codepawfect.clockinservice.application.out.auth.SaveUserPort;
import github.codepawfect.clockinservice.application.out.auth.UserExistsPort;
import github.codepawfect.clockinservice.domain.user.model.NewUser;
import github.codepawfect.clockinservice.shared.exception.UserAlreadyExistsException;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterUserService implements RegisterUseCase {

  private final PasswordEncoder passwordEncoder;
  private final SaveUserPort saveUserPort;
  private final UserExistsPort userExistsPort;

  /** {@inheritDoc} */
  @Override
  public void execute(String username, String password) throws UserAlreadyExistsException {
    NewUser newUser =
        new NewUser(username, passwordEncoder.encode(password), Collections.singletonList("USER"));

    var exists = userExistsPort.existsByUsername(username);

    if (exists) {
      throw new UserAlreadyExistsException("User with username " + username + " already exists.");
    }

    saveUserPort.save(newUser);
  }
}
