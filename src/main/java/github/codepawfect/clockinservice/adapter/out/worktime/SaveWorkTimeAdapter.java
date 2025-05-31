package github.codepawfect.clockinservice.adapter.out.worktime;

import github.codepawfect.clockinservice.adapter.out.worktime.model.WorkTimeDocument;
import github.codepawfect.clockinservice.adapter.out.worktime.repository.WorkTimeRepository;
import github.codepawfect.clockinservice.application.out.worktime.SaveWorkTimePort;
import github.codepawfect.clockinservice.domain.worktime.model.WorkTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SaveWorkTimeAdapter implements SaveWorkTimePort {

  private final WorkTimeRepository workTimeRepository;

  /** {@inheritDoc} */
  @Override
  public String save(WorkTime workTime) {
    return workTimeRepository.save(WorkTimeDocument.from(workTime)).id();
  }
}
