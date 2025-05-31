package github.codepawfect.clockinservice.application.in.worktime.usecase;

import java.time.LocalDate;

public interface CreateWorkTimeUseCase {

  /**
   * Creates a new work time entry for a user on a specific date with the given hours worked and an
   * optional note.
   *
   * @param command the command containing the username, date, hours worked, and an optional note
   * @return the ID of the created work time entry
   */
  CreateWorkTimeResponseDTO execute(CreateWorkTimeCommandDTO command);

  record CreateWorkTimeResponseDTO(String id) {}

  record CreateWorkTimeCommandDTO(
      String username, LocalDate date, Integer hoursWorked, String note) {
    public CreateWorkTimeCommandDTO {
      if (username == null || username.isBlank()) {
        throw new IllegalArgumentException("Username must not be null or blank");
      }
      if (date == null) {
        throw new IllegalArgumentException("Date must not be null");
      }
      if (hoursWorked == null || hoursWorked < 0) {
        throw new IllegalArgumentException("Hours worked must not be null or negative");
      }
    }
  }
}
