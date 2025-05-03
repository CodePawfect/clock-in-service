package github.codepawfect.clockinservice.adapter.driving.user.out;

import github.codepawfect.clockinservice.adapter.driving.user.out.repository.UserRepository;
import github.codepawfect.clockinservice.application.driving.auth.UserExistsPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserExistsAdapter implements UserExistsPort {

    private final UserRepository userRepository;

    /** {@inheritDoc} */
    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }
}
