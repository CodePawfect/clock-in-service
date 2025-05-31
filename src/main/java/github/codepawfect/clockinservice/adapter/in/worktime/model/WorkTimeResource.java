package github.codepawfect.clockinservice.adapter.in.worktime.model;

import github.codepawfect.clockinservice.application.in.worktime.usecase.GetWorkTimesUseCase;
import java.time.LocalDate;

/** WorkTime is a dto class that represents a work time entry. */
public record WorkTimeResource(
    String id,
    String username,
    LocalDate date,
    Integer hoursWorked,
    Integer year,
    Integer calenderWeek,
    String note) {

  public static WorkTimeResource from(GetWorkTimesUseCase.WorkTimeDTO workTimeDTO) {
    return new WorkTimeResource(
        workTimeDTO.id(),
        workTimeDTO.username(),
        workTimeDTO.date(),
        workTimeDTO.hoursWorked(),
        workTimeDTO.year(),
        workTimeDTO.calenderWeek(),
        workTimeDTO.note());
  }
}
