package github.codepawfect.clockinservice.application.in.auth.usecase;

import github.codepawfect.clockinservice.shared.exception.UserAlreadyExistsException;

public interface RegisterUserUseCase {

  /**
   * Registers a new user with the provided username and password.
   *
   * @param command the command containing the username and password
   * @throws UserAlreadyExistsException if a user with the given username already exists
   */
  void execute(RegisterUserCommandDTO command) throws UserAlreadyExistsException;

  record RegisterUserCommandDTO(String username, String password) {}
}
