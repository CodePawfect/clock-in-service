package github.codepawfect.clockinservice.adaptern.out.worktime;

import github.codepawfect.clockinservice.adaptern.out.worktime.model.WorkTimeDocument;
import github.codepawfect.clockinservice.adaptern.out.worktime.repository.WorkTimeRepository;
import github.codepawfect.clockinservice.domain.worktime.model.WorkTime;
import github.codepawfect.clockinservice.domain.worktime.ports.out.WriteWorkTimeToDatabasePort;
import org.springframework.stereotype.Component;

/** WriteWorkTimeToDatabaseAdapter is an adapter for write operations on work time entries. */
@Component
public class WriteWorkTimeToDatabaseAdapter implements WriteWorkTimeToDatabasePort {

  private final WorkTimeRepository workTimeRepository;

  public WriteWorkTimeToDatabaseAdapter(WorkTimeRepository workTimeRepository) {
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
