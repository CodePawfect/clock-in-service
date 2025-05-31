package github.codepawfect.clockinservice.application.in.worktime;

import github.codepawfect.clockinservice.application.in.worktime.usecase.CreateWorkTimeUseCase;
import github.codepawfect.clockinservice.application.out.worktime.SaveWorkTimePort;
import github.codepawfect.clockinservice.domain.worktime.model.WorkTime;
import github.codepawfect.clockinservice.shared.DateUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateWorkTimeService implements CreateWorkTimeUseCase {

  private final SaveWorkTimePort saveWorkTimePort;

  /** {@inheritDoc} */
  @Override
  public CreateWorkTimeResponseDTO execute(CreateWorkTimeCommandDTO command) {
    var newWorktime =
        new WorkTime(
            null,
            command.username(),
            command.date(),
            command.hoursWorked(),
            command.date().getYear(),
            DateUtils.getCalenderWeek(command.date()),
            command.note());

    return new CreateWorkTimeResponseDTO(saveWorkTimePort.save(newWorktime));
  }
}
