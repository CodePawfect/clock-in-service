package github.codepawfect.clockinservice.application.worktime;

import github.codepawfect.clockinservice.domain.worktime.model.WorkTime;
import github.codepawfect.clockinservice.domain.worktime.ports.in.CreateWorkTimeUseCasePort;
import github.codepawfect.clockinservice.domain.worktime.ports.in.DeleteWorkTimeUseCasePort;
import github.codepawfect.clockinservice.domain.worktime.ports.in.GetWorkTimesUseCasePort;
import github.codepawfect.clockinservice.domain.worktime.service.WorkTimeService;
import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Component;

/** This class is responsible for coordinating the different use cases. */
@Component
public class WorkTimeUseCaseOrchestrator
    implements CreateWorkTimeUseCasePort, GetWorkTimesUseCasePort, DeleteWorkTimeUseCasePort {

  private final WorkTimeService workTimeService;

  public WorkTimeUseCaseOrchestrator(WorkTimeService workTimeService) {
    this.workTimeService = workTimeService;
  }

  /** {@inheritDoc} */
  @Override
  public String createWorkTime(String username, LocalDate date, Integer hoursWorked, String note) {

    // TODO: create WorkTime value object which validates itself
    return workTimeService.create(username, date, hoursWorked, note);
  }

  /** {@inheritDoc} */
  @Override
  public void deleteWorkTime(String id) {
    workTimeService.delete(id);
  }

  /** {@inheritDoc} */
  @Override
  public List<WorkTime> getWorkTimes(String username, int calenderWeek, int year) {
    return workTimeService.getAll(username, calenderWeek, year);
  }
}
