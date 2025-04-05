package github.codepawfect.clockinservice.domain.user.ports.in;

import github.codepawfect.clockinservice.domain.user.model.NewUser;

/**
 * CreateUserPort is an interface that defines the contract for creating a new user.
 * It provides a method to create a user with a given username and password.
 */
public interface CreateUserPort {

    /**
     * Creates a new user with the given username and password.
     *
     * @param newUser the new user to be created
     */
    void createUser(NewUser newUser);
}
