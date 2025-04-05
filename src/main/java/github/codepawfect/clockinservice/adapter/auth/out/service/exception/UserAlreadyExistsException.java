package github.codepawfect.clockinservice.adapter.auth.out.service.exception;

public class UserAlreadyExistsException extends RuntimeException {
  public UserAlreadyExistsException(String message) {
    super(message);
  }
}
