package github.codepawfect.clockinservice.domain.user.model;

import java.util.List;

/**
 * NewUser is a record class that represents a new user with a username, password, and a collection
 *
 * @param username the username of the user
 * @param password the password of the user
 * @param roles the collection of roles associated with the user
 */
public record NewUser(String username, String password, List<String> roles) {

    public NewUser {
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("Username cannot be null or blank");
        }
        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("Password cannot be null or blank");
        }
        if (roles == null || roles.isEmpty()) {
            throw new IllegalArgumentException("Roles cannot be null or empty");
        }
    }
}
