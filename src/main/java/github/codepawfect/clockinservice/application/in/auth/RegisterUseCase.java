package github.codepawfect.clockinservice.application.in.auth;

import github.codepawfect.clockinservice.shared.exception.UserAlreadyExistsException;

public interface RegisterUseCase {

  /**
   * Registers a new user with the given username and password.
   *
   * @param username the username of the new user
   * @param password the password of the new user
   * @throws UserAlreadyExistsException if a user with the given username already exists
   */
  void execute(String username, String password) throws UserAlreadyExistsException;
}
