package github.codepawfect.clockinservice.domain.user.model;

import java.util.List;

/**
 * User is a record class that represents a user with a username and a collection of roles.
 *
 * @param username the username of the user
 * @param roles the collection of roles associated with the user
 */
public record User(String username, List<String> roles) {}
