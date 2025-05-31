package github.codepawfect.clockinservice.application.in.worktime;

import github.codepawfect.clockinservice.application.in.worktime.usecase.GetWorkTimesUseCase;
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
  public GetWorkTimesResponseDTO execute(GetWorkTimesQueryDTO query) {
    List<WorkTimeDTO> worktimeDtos =
        findWorkTimesPort.getAll(query.username(), query.calenderWeek(), query.year()).stream()
            .map(this::mapToDto)
            .toList();

    return new GetWorkTimesResponseDTO(worktimeDtos);
  }

  private WorkTimeDTO mapToDto(WorkTime workTime) {
    return new WorkTimeDTO(
        workTime.id(),
        workTime.username(),
        workTime.date(),
        workTime.hoursWorked(),
        workTime.year(),
        workTime.calenderWeek(),
        workTime.note());
  }
}
