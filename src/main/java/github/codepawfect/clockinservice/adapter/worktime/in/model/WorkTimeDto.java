package github.codepawfect.clockinservice.adapter.worktime.in.model;

import java.time.LocalDate;

/** WorkTime is a dto class that represents a work time entry. */
public record WorkTimeDto(
    String id,
    String username,
    LocalDate date,
    Integer hoursWorked,
    Integer year,
    Integer calenderWeek,
    String note) {}
