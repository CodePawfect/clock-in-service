package github.codepawfect.clockinservice.application.auth.port;

import org.springframework.stereotype.Component;

@Component
public interface UserExistsPort {

    /**
     * Checks if a user with the given username exists.
     *
     * @param username the username to check
     * @return true if a user with the given username exists, false otherwise
     */
    boolean existsByUsername(String username);
}
