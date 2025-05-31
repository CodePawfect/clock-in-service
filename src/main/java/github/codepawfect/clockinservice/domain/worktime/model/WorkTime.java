package github.codepawfect.clockinservice.domain.worktime.model;

import java.time.LocalDate;

/** WorkTime is a model class that represents a work time entry. */
public record WorkTime(
    String id,
    String username,
    LocalDate date,
    Integer hoursWorked,
    Integer year,
    Integer calenderWeek,
    String note) {

  public WorkTime {
    if (hoursWorked < 0 || hoursWorked > 24) {
      throw new IllegalArgumentException("Hours worked must be between 0 and 24");
    }

    if (calenderWeek < 1 || calenderWeek > 53) {
      throw new IllegalArgumentException("Calendar week must be between 1 and 53");
    }

    if (year < 1970 || year > 2100) {
      throw new IllegalArgumentException("Year must be between 1970 and 2100");
    }
  }
}
