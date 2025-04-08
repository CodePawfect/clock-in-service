package github.codepawfect.clockinservice.domain.user.service;

import github.codepawfect.clockinservice.domain.user.model.NewUser;
import github.codepawfect.clockinservice.domain.user.model.User;
import github.codepawfect.clockinservice.domain.user.ports.out.ReadUserFromDatabasePort;
import github.codepawfect.clockinservice.domain.user.ports.out.WriteUserToDatabasePort;
import org.springframework.stereotype.Service;

/**
 * UserService is a service for managing users. It implements the GetUserPort and CreateUserPort
 * interfaces.
 */
@Service
public class UserService {

  private final ReadUserFromDatabasePort readUserFromDatabasePort;
  private final WriteUserToDatabasePort writeUserToDatabasePort;

  public UserService(
      ReadUserFromDatabasePort readUserFromDatabasePort,
      WriteUserToDatabasePort writeUserToDatabasePort) {
    this.readUserFromDatabasePort = readUserFromDatabasePort;
    this.writeUserToDatabasePort = writeUserToDatabasePort;
  }

  /**
   * Creates a new user in the database.
   *
   * @param newUser the new user to create
   */
  public void create(NewUser newUser) {
    writeUserToDatabasePort.save(newUser);
  }

  /**
   * Retrieves a user by username.
   *
   * @param username the username of the user to retrieve
   * @return the user with the given username
   */
  public User getByUsername(String username) {
    return readUserFromDatabasePort.findByUsername(username);
  }

  /**
   * Retrieves a user by ID.
   *
   * @param id the ID of the user to retrieve
   * @return the user with the given ID
   */
  public User getById(String id) {
    return readUserFromDatabasePort.findById(id);
  }

  /**
   * Checks if a user exists by username.
   *
   * @param username the username to check
   * @return true if the user exists, false otherwise
   */
  public boolean existsByUsername(String username) {
    return readUserFromDatabasePort.existsByUsername(username);
  }
}
