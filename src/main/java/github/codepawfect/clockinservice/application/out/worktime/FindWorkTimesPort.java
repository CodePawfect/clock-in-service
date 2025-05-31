package github.codepawfect.clockinservice.application.out.worktime;

import github.codepawfect.clockinservice.domain.worktime.model.WorkTime;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public interface FindWorkTimesPort {

  /**
   * Retrieves all work time entries for a user in a specific calendar week and year.
   *
   * @param username the username of the user
   * @param calenderWeek the calendar week
   * @param year the year
   * @return a list of work time entries
   */
  List<WorkTime> getAll(String username, int calenderWeek, int year);
}
