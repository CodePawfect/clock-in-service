package github.codepawfect.clockinservice.domain.worktime.service;

import github.codepawfect.clockinservice.domain.common.DateUtils;
import github.codepawfect.clockinservice.domain.worktime.model.WorkTime;
import github.codepawfect.clockinservice.domain.worktime.ports.out.ReadWorkTimeFromDatabasePort;
import github.codepawfect.clockinservice.domain.worktime.ports.out.WriteWorkTimeToDatabasePort;
import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;

/** WorkTimeService is the domain service for handling work time operations. */
@Service
public class WorkTimeService {

  private final WriteWorkTimeToDatabasePort writeWorkTimeToDatabasePort;
  private final ReadWorkTimeFromDatabasePort readWorkTimeFromDatabasePort;

  public WorkTimeService(
      WriteWorkTimeToDatabasePort writeWorkTimeToDatabasePort,
      ReadWorkTimeFromDatabasePort readWorkTimeFromDatabasePort) {
    this.writeWorkTimeToDatabasePort = writeWorkTimeToDatabasePort;
    this.readWorkTimeFromDatabasePort = readWorkTimeFromDatabasePort;
  }

  /**
   * Creates a new work time entry.
   *
   * @param username the username of the user
   * @param date the date of the work time entry
   * @param hoursWorked the number of hours worked
   * @param note an optional note for the work time entry
   * @return the ID of the created work time entry
   */
  public String create(String username, LocalDate date, Integer hoursWorked, String note) {
    return writeWorkTimeToDatabasePort.save(
        new WorkTime(
            null,
            username,
            date,
            hoursWorked,
            date.getYear(),
            DateUtils.getCalenderWeek(date),
            note));
  }

  /**
   * Retrieves all work time entries for a user in a specific calendar week and year.
   *
   * @param username the username of the user
   * @param calenderWeek the calendar week
   * @param year the year
   * @return a list of work time entries
   */
  public List<WorkTime> getAll(String username, int calenderWeek, int year) {
    return readWorkTimeFromDatabasePort.find(username, calenderWeek, year);
  }

  /**
   * Deletes a work time entry by its ID.
   *
   * @param id the ID of the work time entry to delete
   */
  public void delete(String id) {
    writeWorkTimeToDatabasePort.delete(id);
  }
}
