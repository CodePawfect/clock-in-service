package github.codepawfect.clockinservice.domain.worktime.ports.out;

import github.codepawfect.clockinservice.domain.worktime.model.WorkTime;

import java.util.List;

/**
 * ReadWorkTimePort is a port for read operations on work times.
 */
public interface ReadWorkTimePort {

    /**
     * Finds work time entries for a user in a specific calender week and year.
     *
     * @param username the username of the user
     * @param calenderWeek the calender week
     * @param year the year
     *
     * @return a list of work time entries
     */
    List<WorkTime> find(String username, int calenderWeek, int year);
}
