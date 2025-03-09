package github.codepawfect.clockinservice.common;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
            "Validation failed",
            validationErrors,
            LocalDateTime.now());
    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
  }

  /** ErrorResponse is a record for an error response. */
  public record ErrorResponse(int status, String error, Object message, LocalDateTime timestamp) {}
}
