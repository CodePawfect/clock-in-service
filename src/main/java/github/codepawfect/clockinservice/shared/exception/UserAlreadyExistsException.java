package github.codepawfect.clockinservice.shared.exception;

/**
 * UserAlreadyExistsException is a custom exception that is thrown when a user with the same
 * username already exists in the system.
 */
public class UserAlreadyExistsException extends RuntimeException {

  /**
   * Constructs a new UserAlreadyExistsException with the specified detail message.
   *
   * @param message the detail message
   */
  public UserAlreadyExistsException(String message) {
    super(message);
  }
}
