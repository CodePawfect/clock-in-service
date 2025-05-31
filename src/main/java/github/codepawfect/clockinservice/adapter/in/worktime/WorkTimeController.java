package github.codepawfect.clockinservice.adapter.in.worktime;

import github.codepawfect.clockinservice.adapter.in.worktime.api.WorkTimeApi;
import github.codepawfect.clockinservice.adapter.in.worktime.model.CreateWorkTimeRequest;
import github.codepawfect.clockinservice.adapter.in.worktime.model.GetWorkTimesResponse;
import github.codepawfect.clockinservice.adapter.in.worktime.model.WorkTimeResource;
import github.codepawfect.clockinservice.application.in.worktime.usecase.CreateWorkTimeUseCase;
import github.codepawfect.clockinservice.application.in.worktime.usecase.DeleteWorkTimeUseCase;
import github.codepawfect.clockinservice.application.in.worktime.usecase.GetWorkTimesUseCase;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import java.net.URI;
import java.security.Principal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/** WorkTimeController is a controller for handling work time requests. */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/worktimes")
public class WorkTimeController implements WorkTimeApi {

  private final CreateWorkTimeUseCase createWorkTimeUseCase;
  private final GetWorkTimesUseCase getWorkTimesUseCase;
  private final DeleteWorkTimeUseCase deleteWorkTimeUseCase;

  @Override
  @PostMapping()
  public ResponseEntity<URI> createWorkTime(
      @RequestBody @Valid CreateWorkTimeRequest createWorkTimeRequest, Principal principal) {
    String username = principal.getName();

    CreateWorkTimeUseCase.CreateWorkTimeCommandDTO command =
        new CreateWorkTimeUseCase.CreateWorkTimeCommandDTO(
            username,
            createWorkTimeRequest.date(),
            createWorkTimeRequest.hoursWorked(),
            createWorkTimeRequest.note());

    CreateWorkTimeUseCase.CreateWorkTimeResponseDTO response =
        createWorkTimeUseCase.execute(command);

    URI location =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(response.id())
            .toUri();

    return ResponseEntity.created(location).build();
  }

  @Override
  @GetMapping("/{calenderWeek}/{year}")
  public ResponseEntity<GetWorkTimesResponse> getWorkTimes(
      @Parameter(
              description = "Calender week of the year",
              example = "34",
              required = true,
              schema = @Schema(minimum = "1", maximum = "53"))
          @PathVariable
          @Min(1)
          @Max(53)
          int calenderWeek,
      @Parameter(description = "Year", example = "2025", required = true) @PathVariable int year,
      Principal principal) {
    String username = principal.getName();

    GetWorkTimesUseCase.GetWorkTimesQueryDTO query =
        new GetWorkTimesUseCase.GetWorkTimesQueryDTO(username, calenderWeek, year);

    GetWorkTimesUseCase.GetWorkTimesResponseDTO response = getWorkTimesUseCase.execute(query);

    List<WorkTimeResource> workTimeResources =
        response.workTimes().stream().map(WorkTimeResource::from).toList();

    GetWorkTimesResponse getWorkTimesResponse = new GetWorkTimesResponse(workTimeResources);

    return ResponseEntity.ok(getWorkTimesResponse);
  }

  @Override
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteWorkTime(
      @Parameter(description = "ID of the work time entry", example = "12345", required = true)
          @PathVariable
          @NotBlank
          @Valid
          String id) {
    deleteWorkTimeUseCase.execute(new DeleteWorkTimeUseCase.DeleteWorkTimeCommandDTO(id));

    return ResponseEntity.noContent().build();
  }
}
