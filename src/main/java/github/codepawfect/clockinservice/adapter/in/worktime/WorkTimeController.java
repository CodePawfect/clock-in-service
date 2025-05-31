package github.codepawfect.clockinservice.adapter.in.worktime;

import github.codepawfect.clockinservice.adapter.in.worktime.model.CreateWorkTimeRequest;
import github.codepawfect.clockinservice.adapter.in.worktime.model.GetWorkTimesResponse;
import github.codepawfect.clockinservice.adapter.in.worktime.model.WorkTimeResource;
import github.codepawfect.clockinservice.application.in.worktime.usecase.CreateWorkTimeUseCase;
import github.codepawfect.clockinservice.application.in.worktime.usecase.DeleteWorkTimeUseCase;
import github.codepawfect.clockinservice.application.in.worktime.usecase.GetWorkTimesUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Work Time", description = "Work time management API")
public class WorkTimeController {

  private final CreateWorkTimeUseCase createWorkTimeUseCase;
  private final GetWorkTimesUseCase getWorkTimesUseCase;
  private final DeleteWorkTimeUseCase deleteWorkTimeUseCase;

  /**
   * Creates a new work time entry.
   *
   * @param createWorkTimeRequest the create work time request
   */
  @PostMapping()
  @Operation(
      summary = "Create a new work time entry",
      description = "Creates a work time entry for the authenticated user",
      responses = {
        @ApiResponse(
            responseCode = "201",
            description = "Work time created successfully",
            content = @Content(schema = @Schema())),
        @ApiResponse(responseCode = "400", description = "Invalid input data")
      })
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

  /**
   * Gets work times for a specific calendar week and year.
   *
   * @param calenderWeek the calendar week
   * @param year the year
   */
  @GetMapping("/{calenderWeek}/{year}")
  @Operation(
      summary = "Get work times for a specific calendar week and year",
      description =
          "Gets work times for the authenticated user for a specific calendar week and year",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "Work times retrieved successfully",
            content = @Content(schema = @Schema(implementation = GetWorkTimesResponse.class)))
      })
  public ResponseEntity<GetWorkTimesResponse> getWorkTimes(
      @Schema(
              description = "Calender week of the year",
              example = "34",
              requiredMode = Schema.RequiredMode.REQUIRED)
          @PathVariable
          @Min(1)
          @Max(53)
          int calenderWeek,
      @Schema(description = "Year", example = "2025", requiredMode = Schema.RequiredMode.REQUIRED)
          @PathVariable
          int year,
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

  /**
   * Deletes a work time entry by id.
   *
   * @param id the id of the work time entry
   */
  @DeleteMapping("/{id}")
  @Operation(
      summary = "Delete a work time entry by id",
      description = "Deletes a work time entry for the authenticated user by id",
      responses = {
        @ApiResponse(
            responseCode = "204",
            description = "Work time deleted successfully",
            content = @Content(schema = @Schema()))
      })
  public ResponseEntity<Void> deleteWorkTime(
      @Schema(
              description = "ID of the work time entry",
              example = "12345",
              requiredMode = Schema.RequiredMode.REQUIRED)
          @PathVariable
          @NotBlank
          @Valid
          String id) {
    deleteWorkTimeUseCase.execute(new DeleteWorkTimeUseCase.DeleteWorkTimeCommandDTO(id));

    return ResponseEntity.noContent().build();
  }
}
