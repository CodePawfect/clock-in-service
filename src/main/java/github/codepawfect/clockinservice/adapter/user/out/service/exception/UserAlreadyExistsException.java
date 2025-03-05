package github.codepawfect.clockinservice.adapter.user.out.service.exception;

public class UserAlreadyExistsException extends RuntimeException {
  public UserAlreadyExistsException(String message) {
    super(message);
  }
}
