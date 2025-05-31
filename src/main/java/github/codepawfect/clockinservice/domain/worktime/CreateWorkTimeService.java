package github.codepawfect.clockinservice.domain.worktime;

import github.codepawfect.clockinservice.application.in.worktime.CreateWorkTimeUseCase;
import github.codepawfect.clockinservice.application.out.worktime.SaveWorkTimePort;
import github.codepawfect.clockinservice.domain.worktime.model.WorkTime;
import github.codepawfect.clockinservice.shared.DateUtils;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateWorkTimeService implements CreateWorkTimeUseCase {

  private final SaveWorkTimePort saveWorkTimePort;

  /** {@inheritDoc} */
  @Override
  public String execute(String username, LocalDate date, Integer hoursWorked, String note) {
    var newWorktime =
        new WorkTime(
            null,
            username,
            date,
            hoursWorked,
            date.getYear(),
            DateUtils.getCalenderWeek(date),
            note);

    return saveWorkTimePort.save(newWorktime);
  }
}
