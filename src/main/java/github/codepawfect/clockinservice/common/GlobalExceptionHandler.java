package github.codepawfect.clockinservice.common;

import github.codepawfect.clockinservice.adapter.auth.out.service.exception.UserAlreadyExistsException;
import io.jsonwebtoken.JwtException;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/** GlobalExceptionHandler is a class that handles exceptions globally. */
@ControllerAdvice
public class GlobalExceptionHandler {

  /**
   * Handles a generic exception.
   *
   * @param ex the exception to handle.
   * @return a response entity with an error response.
   */
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
    ErrorResponse errorResponse =
        new ErrorResponse(
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            "An unexpected error occurred",
            ex.getMessage(),
            LocalDateTime.now());
    return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  /**
   * Handles an authentication exception.
   *
   * @param ex the exception to handle.
   * @return a response entity with an error response.
   */
  @ExceptionHandler(AuthenticationException.class)
  public ResponseEntity<ErrorResponse> handleAuthenticationException(AuthenticationException ex) {
    ErrorResponse errorResponse =
        new ErrorResponse(
            HttpStatus.UNAUTHORIZED.value(),
            "Username or password is incorrect",
            ex.getMessage(),
            LocalDateTime.now());
    return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
  }

  /**
   * Handles a user already exists exception.
   *
   * @param ex the exception to handle.
   * @return a response entity with an error response.
   */
  @ExceptionHandler(UserAlreadyExistsException.class)
  public ResponseEntity<ErrorResponse> handleUserAlreadyExistsException(
      UserAlreadyExistsException ex) {
    ErrorResponse errorResponse =
        new ErrorResponse(
            HttpStatus.CONFLICT.value(),
            "Username already exists",
            ex.getMessage(),
            LocalDateTime.now());
    return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
  }

  @ExceptionHandler(JwtException.class)
  public ResponseEntity<ErrorResponse> handleJwtException(JwtException ex) {
    ErrorResponse errorResponse =
        new ErrorResponse(
            HttpStatus.UNAUTHORIZED.value(),
            "Authentication token is invalid",
            ex.getMessage(),
            LocalDateTime.now());
    return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
  }

  /**
   * Handles a validation exception.
   *
   * @param ex the exception to handle.
   * @return a response entity with an error response.
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> handleValidationException(
      MethodArgumentNotValidException ex) {
    List<String> validationErrors =
        ex.getBindingResult().getFieldErrors().stream()
            .map(error -> error.getField() + ": " + error.getDefaultMessage())
            .toList();

    ErrorResponse errorResponse =
        new ErrorResponse(
            HttpStatus.BAD_REQUEST.value(),
            "Method argument validation failed",
            validationErrors,
            LocalDateTime.now());
    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
  }

  /** ErrorResponse is a record for an error response. */
  public record ErrorResponse(int status, String error, Object message, LocalDateTime timestamp) {}
}
