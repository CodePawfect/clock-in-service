package github.codepawfect.clockinservice.domain.user.ports.in;

import github.codepawfect.clockinservice.domain.user.model.User;

/**
 * GetUserPort is an interface that defines the contract for retrieving user information.
 * It provides methods to get a user by their username or ID.
 */
public interface GetUserPort {

    /**
     * Retrieves a user by their username.
     *
     * @param username the username of the user to retrieve
     * @return the user with the specified username
     */
    User getUserByUsername(String username);

    /**
     * Retrieves a user by their ID.
     *
     * @param id the ID of the user to retrieve
     * @return the user with the specified ID
     */
    User getUserById(String id);

    /**
     * Checks if a user with the specified username exists.
     *
     * @param username the username to check
     * @return true if a user with the specified username exists, false otherwise
     */
    boolean existsByUsername(String username);
}
