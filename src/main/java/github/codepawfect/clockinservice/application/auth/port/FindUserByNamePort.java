package github.codepawfect.clockinservice.application.auth.port;

import github.codepawfect.clockinservice.domain.user.model.User;
import org.springframework.stereotype.Component;

@Component
public interface FindUserByNamePort {

    /**
     * Finds a user by their username.
     *
     * @param username the username of the user
     * @return the user with the given username
     */
    User findByUsername(String username);
}
