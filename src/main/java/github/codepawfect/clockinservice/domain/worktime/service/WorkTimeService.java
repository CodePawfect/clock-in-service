package github.codepawfect.clockinservice.domain.worktime.service;

import github.codepawfect.clockinservice.common.DateUtils;
import github.codepawfect.clockinservice.domain.worktime.model.WorkTime;
import github.codepawfect.clockinservice.domain.worktime.ports.in.CreateWorkTimeUseCasePort;
import github.codepawfect.clockinservice.domain.worktime.ports.in.DeleteWorkTimeUseCasePort;
import github.codepawfect.clockinservice.domain.worktime.ports.in.GetWorkTimesUseCasePort;
import github.codepawfect.clockinservice.domain.worktime.ports.out.ReadWorkTimeFromDatabasePort;
import github.codepawfect.clockinservice.domain.worktime.ports.out.WriteWorkTimeToDatabasePort;
import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;

/** WorkTimeService is a service for handling work time operations. */
@Service
public class WorkTimeService implements CreateWorkTimeUseCasePort, GetWorkTimesUseCasePort, DeleteWorkTimeUseCasePort {

  private final WriteWorkTimeToDatabasePort writeWorkTimeToDatabasePort;
  private final ReadWorkTimeFromDatabasePort readWorkTimeFromDatabasePort;

  public WorkTimeService(WriteWorkTimeToDatabasePort writeWorkTimeToDatabasePort, ReadWorkTimeFromDatabasePort readWorkTimeFromDatabasePort) {
    this.writeWorkTimeToDatabasePort = writeWorkTimeToDatabasePort;
    this.readWorkTimeFromDatabasePort = readWorkTimeFromDatabasePort;
  }

  /** {@inheritDoc} */
  @Override
  public String createWorkTime(String username, LocalDate date, Integer hoursWorked, String note) {
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

  /** {@inheritDoc} */
  @Override
  public List<WorkTime> getWorkTimes(String username, int calenderWeek, int year) {
    return readWorkTimeFromDatabasePort.find(username, calenderWeek, year);
  }

  /** {@inheritDoc} */
  @Override
  public void deleteWorkTime(String id) {
    writeWorkTimeToDatabasePort.delete(id);
  }
}
