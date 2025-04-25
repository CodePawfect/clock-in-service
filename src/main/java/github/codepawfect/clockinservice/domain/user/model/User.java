package github.codepawfect.clockinservice.domain.user.model;

import java.util.List;

/**
 * User is a record class that represents a user with a username and a collection of roles.
 *
 * @param username the username of the user
 * @param roles the collection of roles associated with the user
 */
public record User(String username, List<String> roles) {

  public User {
    if (username == null || username.isBlank()) {
      throw new IllegalArgumentException("Username cannot be null or blank");
    }
    if (roles == null || roles.isEmpty()) {
      throw new IllegalArgumentException("Roles cannot be null or empty");
    }
  }
}
