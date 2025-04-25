package github.codepawfect.clockinservice.adapter.out.user.out;

import github.codepawfect.clockinservice.adapter.out.user.out.model.UserDocument;
import github.codepawfect.clockinservice.adapter.out.user.out.repository.UserRepository;
import github.codepawfect.clockinservice.application.auth.port.FindUserByIdPort;
import github.codepawfect.clockinservice.domain.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FindUserByIdAdapter implements FindUserByIdPort {

    private final UserRepository userRepository;

    /** {@inheritDoc} */
    @Override
    public User findById(String id) {
        return userRepository
        .findById(id)
        .map(UserDocument::toDomain)
        .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + id));
    }
}
