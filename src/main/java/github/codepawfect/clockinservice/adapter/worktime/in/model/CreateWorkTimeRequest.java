package github.codepawfect.clockinservice.adapter.worktime.in.model;

import java.time.LocalDate;

/**
 * CreateWorkTimeRequest is a model class that represents the request body for the create work time endpoint.
 */
public record CreateWorkTimeRequest(LocalDate date, Integer hoursWorked) {
}
