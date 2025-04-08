package github.codepawfect.clockinservice.domain.user.ports.in;

import github.codepawfect.clockinservice.adaptern.in.common.exception.UserAlreadyExistsException;

/** RegisterUseCasePort is an interface for the use case of registering a new user. */
public interface RegisterUseCasePort {

  /**
   * Registers a new user with the given username and password.
   *
   * @param username the username of the new user
   * @param password the password of the new user
   * @throws UserAlreadyExistsException if a user with the given username already exists
   */
  void register(String username, String password) throws UserAlreadyExistsException;
}
