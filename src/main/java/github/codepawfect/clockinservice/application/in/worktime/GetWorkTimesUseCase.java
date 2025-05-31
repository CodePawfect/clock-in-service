package github.codepawfect.clockinservice.application.in.worktime;

import github.codepawfect.clockinservice.domain.worktime.model.WorkTime;
import java.util.List;

public interface GetWorkTimesUseCase {

  /**
   * Reads work times for a user in a specific calender week and year.
   *
   * @param username the username of the user
   * @param calenderWeek the calender week
   * @param year the year
   * @return a list of work times
   */
  List<WorkTime> execute(String username, int calenderWeek, int year);
}
