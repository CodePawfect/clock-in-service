package github.codepawfect.clockinservice.adapter.out.user.out;

import github.codepawfect.clockinservice.adapter.out.user.out.model.UserDocument;
import github.codepawfect.clockinservice.adapter.out.user.out.repository.UserRepository;
import github.codepawfect.clockinservice.application.auth.port.FindUserByNamePort;
import github.codepawfect.clockinservice.domain.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FindUserByNameAdapter implements FindUserByNamePort {

    private final UserRepository userRepository;

    /** {@inheritDoc} */
    @Override
    public User findByUsername(String username) {
        return userRepository
        .findByUsername(username)
        .map(UserDocument::toDomain)
        .orElseThrow(
            () -> new UsernameNotFoundException("User not found with username: " + username));
    }
}
