package github.codepawfect.clockinservice.application.auth.port;

import github.codepawfect.clockinservice.domain.user.model.User;
import org.springframework.stereotype.Component;

@Component
public interface FindUserByIdPort {

    /**
     * Finds a user by their ID.
     *
     * @param id the ID of the user
     * @return the user with the given ID
     */
    User findById(String id);
}
