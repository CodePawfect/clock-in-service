package github.codepawfect.clockinservice.domain.user.model;

import java.util.List;

/**
 * NewUser is a record class that represents a new user with a username, password, and a collection
 *
 * @param username the username of the user
 * @param password the password of the user
 * @param roles the collection of roles associated with the user
 */
public record NewUser(String username, String password, List<String> roles) {}
