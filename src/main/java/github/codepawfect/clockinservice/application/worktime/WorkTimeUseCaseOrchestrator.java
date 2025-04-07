package github.codepawfect.clockinservice.application.worktime;

import github.codepawfect.clockinservice.domain.worktime.model.WorkTime;
import github.codepawfect.clockinservice.domain.worktime.ports.in.CreateWorkTimeUseCasePort;
import github.codepawfect.clockinservice.domain.worktime.ports.in.DeleteWorkTimeUseCasePort;
import github.codepawfect.clockinservice.domain.worktime.ports.in.GetWorkTimesUseCasePort;
import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Component;

/** This class is responsible for coordinating the different use cases. */
@Component
public class WorkTimeUseCaseOrchestrator {

  private final CreateWorkTimeUseCasePort createWorkTimeUseCasePort;
  private final GetWorkTimesUseCasePort getWorkTimesUseCasePort;
  private final DeleteWorkTimeUseCasePort deleteWorkTimeUseCasePort;

  public WorkTimeUseCaseOrchestrator(
      CreateWorkTimeUseCasePort createWorkTimeUseCasePort,
      GetWorkTimesUseCasePort getWorkTimesUseCasePort,
      DeleteWorkTimeUseCasePort deleteWorkTimeUseCasePort) {
    this.createWorkTimeUseCasePort = createWorkTimeUseCasePort;
    this.getWorkTimesUseCasePort = getWorkTimesUseCasePort;
    this.deleteWorkTimeUseCasePort = deleteWorkTimeUseCasePort;
  }

  /**
   * Creates a new work time entry.
   *
   * @param username the username for the work time entry
   * @param date the date for the work time entry
   * @param hoursWorked the number of hours worked
   * @param note the note for the work time entry
   * @return identifier for created work time entry
   */
  public String createNewWorkTime(
      String username, LocalDate date, Integer hoursWorked, String note) {
    return createWorkTimeUseCasePort.createWorkTime(username, date, hoursWorked, note);
  }

  /**
   * Gets work times for a user in a specific calendar week and year.
   *
   * @param username the username for the work time entry
   * @param calenderWeek the calendar week
   * @param year the year
   * @return a list of work times for the user in the specified week and year
   */
  public List<WorkTime> getWorkTimes(String username, Integer calenderWeek, Integer year) {
    return getWorkTimesUseCasePort.getWorkTimes(username, calenderWeek, year);
  }

  /**
   * Deletes a work time entry.
   *
   * @param id the id of the work time entry to delete
   */
  public void deleteWorkTime(String id) {
    deleteWorkTimeUseCasePort.deleteWorkTime(id);
  }
}
