package github.codepawfect.clockinservice.adapter.out.user.out;

import github.codepawfect.clockinservice.adapter.out.user.out.model.UserDocument;
import github.codepawfect.clockinservice.adapter.out.user.out.repository.UserRepository;
import github.codepawfect.clockinservice.domain.user.model.NewUser;
import github.codepawfect.clockinservice.domain.user.ports.out.WriteUserToDatabasePort;
import org.springframework.stereotype.Component;

/** WriteUserToDatabaseAdapter is an adapter for write operations on users. */
@Component
public class WriteUserToDatabaseAdapter implements WriteUserToDatabasePort {

  private final UserRepository userRepository;

  public WriteUserToDatabaseAdapter(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  /** {@inheritDoc} */
  @Override
  public void save(NewUser newUser) {
    UserDocument userDocument = UserDocument.from(newUser);

    userRepository.save(userDocument);
  }
}
