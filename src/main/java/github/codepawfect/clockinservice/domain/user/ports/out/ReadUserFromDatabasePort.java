package github.codepawfect.clockinservice.domain.user.ports.out;

import github.codepawfect.clockinservice.domain.user.model.User;

/** ReadUserFromDatabasePort is a port for read operations on users. */
public interface ReadUserFromDatabasePort {

  /**
   * Finds a user by their username.
   *
   * @param username the username of the user
   * @return the user with the given username
   */
  User findByUsername(String username);

  /**
   * Finds a user by their ID.
   *
   * @param id the ID of the user
   * @return the user with the given ID
   */
  User findById(String id);

  /**
   * Checks if a user with the given username exists.
   *
   * @param username the username to check
   * @return true if a user with the given username exists, false otherwise
   */
  boolean existsByUsername(String username);
}
