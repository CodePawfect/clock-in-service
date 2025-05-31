package github.codepawfect.clockinservice.application.in.worktime.usecase;

import java.time.LocalDate;
import java.util.List;

public interface GetWorkTimesUseCase {

  GetWorkTimesResponseDTO execute(GetWorkTimesQueryDTO query);

  record GetWorkTimesResponseDTO(List<WorkTimeDTO> workTimes) {}

  record WorkTimeDTO(
      String id,
      String username,
      LocalDate date,
      Integer hoursWorked,
      Integer year,
      Integer calenderWeek,
      String note) {}

  record GetWorkTimesQueryDTO(String username, int calenderWeek, int year) {}
}
