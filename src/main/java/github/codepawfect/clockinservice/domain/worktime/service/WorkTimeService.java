package github.codepawfect.clockinservice.domain.worktime.service;

import github.codepawfect.clockinservice.common.DateUtils;
import github.codepawfect.clockinservice.domain.worktime.model.WorkTime;
import github.codepawfect.clockinservice.domain.worktime.ports.in.CreateWorkTimePort;
import github.codepawfect.clockinservice.domain.worktime.ports.in.DeleteWorkTimePort;
import github.codepawfect.clockinservice.domain.worktime.ports.in.GetWorkTimesPort;
import github.codepawfect.clockinservice.domain.worktime.ports.out.ReadWorkTimePort;
import github.codepawfect.clockinservice.domain.worktime.ports.out.WriteWorkTimePort;
import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;

/** WorkTimeService is a service for handling work time operations. */
@Service
public class WorkTimeService implements CreateWorkTimePort, GetWorkTimesPort, DeleteWorkTimePort {

  private final WriteWorkTimePort writeWorkTimePort;
  private final ReadWorkTimePort readWorkTimePort;

  public WorkTimeService(WriteWorkTimePort writeWorkTimePort, ReadWorkTimePort readWorkTimePort) {
    this.writeWorkTimePort = writeWorkTimePort;
    this.readWorkTimePort = readWorkTimePort;
  }

  /** {@inheritDoc} */
  @Override
  public String createWorkTime(String username, LocalDate date, Integer hoursWorked, String note) {
    return writeWorkTimePort.save(
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
    return readWorkTimePort.find(username, calenderWeek, year);
  }

  /** {@inheritDoc} */
  @Override
  public void deleteWorkTime(String id) {
    writeWorkTimePort.delete(id);
  }
}
