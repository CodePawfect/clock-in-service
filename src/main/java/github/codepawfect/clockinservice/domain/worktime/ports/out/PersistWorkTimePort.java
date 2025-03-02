package github.codepawfect.clockinservice.domain.worktime.ports.out;

/**
 * PersistWorkTimePort is a port for persisting work time entries.
 */
public interface PersistWorkTimePort {

    /**
     * Persists a new work time entry.
     *
     * @param username the username of the user
     * @param date the date of the work time entry
     * @param hoursWorked the hours worked
     *
     * @return the ID of the created work time entry
     */
    String createWorkTime(String username, String date, Integer hoursWorked);
}
