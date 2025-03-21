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
    String note) {}
