package github.codepawfect.clockinservice.application.in.worktime;

import java.time.LocalDate;

public interface CreateWorkTimeUseCase {

  /**
   * Creates a new work time entry.
   *
   * @param username the username of the user
   * @param date the date of the work time entry
   * @param hoursWorked the hours worked
   * @param note the note for the work time entry
   * @return the ID of the created work time entry
   */
  String execute(String username, LocalDate date, Integer hoursWorked, String note);
}
