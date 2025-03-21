package github.codepawfect.clockinservice.adapter.worktime.in;

import github.codepawfect.clockinservice.adapter.common.JwtUtils;
import github.codepawfect.clockinservice.adapter.worktime.in.model.CreateWorkTimeRequest;
import github.codepawfect.clockinservice.adapter.worktime.in.model.GetWorkTimesResponse;
import github.codepawfect.clockinservice.domain.worktime.model.WorkTime;
import github.codepawfect.clockinservice.domain.worktime.ports.in.CreateWorkTimePort;
import github.codepawfect.clockinservice.domain.worktime.ports.in.DeleteWorkTimePort;
import github.codepawfect.clockinservice.domain.worktime.ports.in.GetWorkTimesPort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import java.net.URI;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/** WorkTimeController is a controller for handling work time requests. */
@RestController
@RequestMapping("/api/worktimes")
@Tag(name = "Work Time", description = "Work time management API")
public class WorkTimeController {

  private final CreateWorkTimePort createWorkTimePort;
  private final GetWorkTimesPort getWorkTimesPort;
  private final DeleteWorkTimePort deleteWorkTimePort;
  private final JwtUtils jwtUtils;

  public WorkTimeController(
      CreateWorkTimePort createWorkTimePort,
      GetWorkTimesPort getWorkTimesPort,
      DeleteWorkTimePort deleteWorkTimePort,
      JwtUtils jwtUtils) {
    this.createWorkTimePort = createWorkTimePort;
    this.getWorkTimesPort = getWorkTimesPort;
    this.deleteWorkTimePort = deleteWorkTimePort;
    this.jwtUtils = jwtUtils;
  }

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
            content = @Content(schema = @Schema(implementation = Void.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input data")
      })
  public ResponseEntity<URI> createWorkTime(
      @RequestBody @Valid CreateWorkTimeRequest createWorkTimeRequest, HttpServletRequest request) {
    String username = jwtUtils.extractUsername(jwtUtils.getJwtFromCookies(request));
    String workTimeId =
        createWorkTimePort.createWorkTime(
            username,
            createWorkTimeRequest.date(),
            createWorkTimeRequest.hoursWorked(),
            createWorkTimeRequest.note());

    URI location =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(workTimeId)
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
      HttpServletRequest request) {
    String username = jwtUtils.extractUsername(jwtUtils.getJwtFromCookies(request));

    List<WorkTime> workTimes = getWorkTimesPort.getWorkTimes(username, calenderWeek, year);
    GetWorkTimesResponse getWorkTimesResponse = new GetWorkTimesResponse(workTimes);

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
            content = @Content(schema = @Schema(implementation = Void.class)))
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
    deleteWorkTimePort.deleteWorkTime(id);

    return ResponseEntity.noContent().build();
  }
}
