package github.codepawfect.clockinservice.domain.worktime.ports.in;

import java.time.LocalDate;

/** CreateWorkTimePort is a port for creating work time entries. */
public interface CreateWorkTimePort {

  /**
   * Creates a new work time entry.
   *
   * @param username the username of the user
   * @param date the date of the work time entry
   * @param hoursWorked the hours worked
   * @param note the note for the work time entry
   * @return the ID of the created work time entry
   */
  String createWorkTime(
      String username,
      LocalDate date,
      Integer hoursWorked,
      String note);
}
