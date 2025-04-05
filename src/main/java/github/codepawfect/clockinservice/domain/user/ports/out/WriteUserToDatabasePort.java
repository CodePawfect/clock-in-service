package github.codepawfect.clockinservice.domain.user.ports.out;

import github.codepawfect.clockinservice.domain.user.model.NewUser;

/** WriteUserToDatabasePort is a port for write operations on users. */
public interface WriteUserToDatabasePort {

  /**
   * Saves a user to the database.
   *
   * @param newUser the user to be saved
   */
  void save(NewUser newUser);
}
