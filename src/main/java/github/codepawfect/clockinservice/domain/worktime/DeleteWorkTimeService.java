package github.codepawfect.clockinservice.domain.worktime;

import github.codepawfect.clockinservice.application.in.worktime.DeleteWorkTimeUseCase;
import github.codepawfect.clockinservice.application.out.worktime.DeleteWorkTimePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteWorkTimeService implements DeleteWorkTimeUseCase {

  private final DeleteWorkTimePort deleteWorkTimePort;

  /** {@inheritDoc} */
  @Override
  public void execute(String id) {
    deleteWorkTimePort.deleteWorkTime(id);
  }
}
