package github.codepawfect.clockinservice.adapter.worktime.out;

import github.codepawfect.clockinservice.adapter.worktime.out.model.WorkTimeDocument;
import github.codepawfect.clockinservice.adapter.worktime.out.repository.WorkTimeRepository;
import github.codepawfect.clockinservice.domain.worktime.model.WorkTime;
import github.codepawfect.clockinservice.domain.worktime.ports.out.ReadWorkTimeFromDatabasePort;
import java.util.List;
import org.springframework.stereotype.Component;

/**
 * ReadWorkTimeFromDatabaseAdapter is an adapter for read operations on work time entries.
 */
@Component
public class ReadWorkTimeFromDatabaseAdapter implements ReadWorkTimeFromDatabasePort {

  private final WorkTimeRepository workTimeRepository;

  public ReadWorkTimeFromDatabaseAdapter(WorkTimeRepository workTimeRepository) {
    this.workTimeRepository = workTimeRepository;
  }

  /** {@inheritDoc} */
  @Override
  public List<WorkTime> find(String username, int calenderWeek, int year) {
    return workTimeRepository
        .findByUsernameAndCalenderWeekAndYear(username, calenderWeek, year)
        .stream()
        .map(WorkTimeDocument::toWorkTime)
        .toList();
  }
}
