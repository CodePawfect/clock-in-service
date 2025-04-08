package github.codepawfect.clockinservice.adaptern.out.user.out;

import github.codepawfect.clockinservice.adaptern.out.user.out.model.UserDocument;
import github.codepawfect.clockinservice.adaptern.out.user.out.repository.UserRepository;
import github.codepawfect.clockinservice.domain.user.model.User;
import github.codepawfect.clockinservice.domain.user.ports.out.ReadUserFromDatabasePort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/** ReadUserFromDatabaseAdapter is an adapter for read operations on users. */
@Component
public class ReadUserFromDatabaseAdapter implements ReadUserFromDatabasePort {

  private final UserRepository userRepository;

  public ReadUserFromDatabaseAdapter(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  /** {@inheritDoc} */
  @Override
  public User findByUsername(String username) {
    return userRepository
        .findByUsername(username)
        .map(UserDocument::toDomain)
        .orElseThrow(
            () -> new UsernameNotFoundException("User not found with username: " + username));
  }

  /** {@inheritDoc} */
  @Override
  public User findById(String id) {
    return userRepository
        .findById(id)
        .map(UserDocument::toDomain)
        .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + id));
  }

  /** {@inheritDoc} */
  @Override
  public boolean existsByUsername(String username) {
    return userRepository.existsByUsername(username);
  }
}
