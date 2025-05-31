package github.codepawfect.clockinservice.application.in.auth;

import github.codepawfect.clockinservice.application.in.auth.usecase.RegisterUserUseCase;
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
public class RegisterUserUserService implements RegisterUserUseCase {

  private final PasswordEncoder passwordEncoder;
  private final SaveUserPort saveUserPort;
  private final UserExistsPort userExistsPort;

  /** {@inheritDoc} */
  @Override
  public void execute(RegisterUserCommandDTO command) throws UserAlreadyExistsException {
    NewUser newUser =
        new NewUser(
            command.username(),
            passwordEncoder.encode(command.password()),
            Collections.singletonList("USER"));

    var exists = userExistsPort.existsByUsername(command.username());

    if (exists) {
      throw new UserAlreadyExistsException(
          "User with username " + command.username() + " already exists.");
    }

    saveUserPort.save(newUser);
  }
}
