package github.codepawfect.clockinservice.domain.worktime;

import github.codepawfect.clockinservice.application.in.worktime.GetWorkTimesUseCase;
import github.codepawfect.clockinservice.application.out.worktime.FindWorkTimesPort;
import github.codepawfect.clockinservice.domain.worktime.model.WorkTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetWorkTimeService implements GetWorkTimesUseCase {

  private final FindWorkTimesPort findWorkTimesPort;

  /** {@inheritDoc} */
  @Override
  public List<WorkTime> execute(String username, int calenderWeek, int year) {
    return findWorkTimesPort.getAll(username, calenderWeek, year);
  }
}
