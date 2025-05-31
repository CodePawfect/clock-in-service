package github.codepawfect.clockinservice.application.in.worktime;

import github.codepawfect.clockinservice.application.in.worktime.usecase.DeleteWorkTimeUseCase;
import github.codepawfect.clockinservice.application.out.worktime.DeleteWorkTimePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteWorkTimeService implements DeleteWorkTimeUseCase {

  private final DeleteWorkTimePort deleteWorkTimePort;

  /** {@inheritDoc} */
  @Override
  public void execute(DeleteWorkTimeCommandDTO command) {
    deleteWorkTimePort.deleteWorkTime(command.id());
  }
}
