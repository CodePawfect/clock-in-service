package github.codepawfect.clockinservice.application.auth.port;

import github.codepawfect.clockinservice.domain.user.model.NewUser;

public interface SaveUserPort {

    /**
     * Saves a user to the database.
     *
     * @param newUser the user to be saved
     */
    void save(NewUser newUser);
}
