package github.codepawfect.clockinservice.adapter.out.user.out;

import github.codepawfect.clockinservice.adapter.out.user.out.model.UserDocument;
import github.codepawfect.clockinservice.adapter.out.user.out.repository.UserRepository;
import github.codepawfect.clockinservice.application.out.auth.SaveUserPort;
import github.codepawfect.clockinservice.domain.user.model.NewUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SaveUserAdapter implements SaveUserPort {

  private final UserRepository userRepository;

  /** {@inheritDoc} */
  @Override
  public void save(NewUser newUser) {
    UserDocument userDocument = UserDocument.from(newUser);

    userRepository.save(userDocument);
  }
}
