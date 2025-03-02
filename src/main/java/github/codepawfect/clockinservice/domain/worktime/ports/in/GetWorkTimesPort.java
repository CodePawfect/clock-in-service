package github.codepawfect.clockinservice.domain.worktime.ports.in;

import github.codepawfect.clockinservice.domain.worktime.model.WorkTime;

import java.util.List;

/**
 * ReadWorkTimesPort is a port that defines the method to read work times for a user in a specific calender week and year.
 */
public interface GetWorkTimesPort {

    /**
     * Reads work times for a user in a specific calender week and year.
     *
     * @param username the username of the user
     * @param calenderWeek the calender week
     * @param year the year
     *
     * @return a list of work times
     */
    List<WorkTime> getWorkTimes(String username, int calenderWeek, int year);
}
