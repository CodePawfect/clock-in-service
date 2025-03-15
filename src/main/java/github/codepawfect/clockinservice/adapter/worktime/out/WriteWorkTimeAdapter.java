package github.codepawfect.clockinservice.adapter.worktime.out;

import github.codepawfect.clockinservice.adapter.worktime.out.model.WorkTimeDocument;
import github.codepawfect.clockinservice.adapter.worktime.out.repository.WorkTimeRepository;
import github.codepawfect.clockinservice.domain.worktime.model.WorkTime;
import github.codepawfect.clockinservice.domain.worktime.ports.out.WriteWorkTimePort;
import org.springframework.stereotype.Component;

/** WriteWorkTimeAdapter is an adapter for write operations on work time entries. */
@Component
public class WriteWorkTimeAdapter implements WriteWorkTimePort {

  private final WorkTimeRepository workTimeRepository;

  public WriteWorkTimeAdapter(WorkTimeRepository workTimeRepository) {
    this.workTimeRepository = workTimeRepository;
  }

  /** {@inheritDoc} */
  @Override
  public String save(WorkTime workTime) {
    return workTimeRepository.save(WorkTimeDocument.from(workTime)).id();
  }

  /** {@inheritDoc} */
  @Override
  public void delete(String id) {
    workTimeRepository.deleteById(id);
  }
}
