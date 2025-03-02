package github.codepawfect.clockinservice.domain.worktime.ports.in;

import java.time.LocalDate;

/**
 * CreateWorkTimePort is a port for creating work time entries.
 */
public interface CreateWorkTimePort {

    /**
     * Creates a new work time entry.
     *
     * @param username the username of the user
     * @param date the date of the work time entry
     * @param hoursWorked the hours worked
     *
     * @return the ID of the created work time entry
     */
    Long createWorkTime(String username, LocalDate date, Integer hoursWorked);
}
