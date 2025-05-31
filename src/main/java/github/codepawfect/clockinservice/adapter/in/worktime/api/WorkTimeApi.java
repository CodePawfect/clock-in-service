package github.codepawfect.clockinservice.adapter.in.worktime.api;

import github.codepawfect.clockinservice.adapter.in.worktime.model.CreateWorkTimeRequest;
import github.codepawfect.clockinservice.adapter.in.worktime.model.GetWorkTimesResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * API interface for handling work time operations.
 * This interface is generated from the OpenAPI specification.
 */
@Tag(name = "Work Time", description = "Work time management API")
@RequestMapping("/api/worktimes")
public interface WorkTimeApi {

  /**
   * Creates a new work time entry.
   *
   * @param createWorkTimeRequest the create work time request
   * @param principal the authenticated user
   * @return the created work time URI
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
        @ApiResponse(responseCode = "400", description = "Invalid input data"),
        @ApiResponse(responseCode = "401", description = "Unauthorized")
      })
  ResponseEntity<URI> createWorkTime(
      @RequestBody @Valid CreateWorkTimeRequest createWorkTimeRequest, Principal principal);

  /**
   * Gets work times for a specific calendar week and year.
   *
   * @param calenderWeek the calendar week
   * @param year the year
   * @param principal the authenticated user
   * @return the work times for the specified calendar week and year
   */
  @GetMapping("/{calenderWeek}/{year}")
  @Operation(
      summary = "Get work times for a specific calendar week and year",
      description = "Gets work times for the authenticated user for a specific calendar week and year",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "Work times retrieved successfully",
            content = @Content(schema = @Schema(implementation = GetWorkTimesResponse.class))),
        @ApiResponse(responseCode = "401", description = "Unauthorized")
      })
  ResponseEntity<GetWorkTimesResponse> getWorkTimes(
      @Parameter(
              description = "Calender week of the year",
              example = "34",
              required = true,
              schema = @Schema(minimum = "1", maximum = "53"))
          @PathVariable
          @Min(1)
          @Max(53)
          int calenderWeek,
      @Parameter(description = "Year", example = "2025", required = true)
          @PathVariable
          int year,
      Principal principal);

  /**
   * Deletes a work time entry by id.
   *
   * @param id the id of the work time entry
   * @return no content response
   */
  @DeleteMapping("/{id}")
  @Operation(
      summary = "Delete a work time entry by id",
      description = "Deletes a work time entry for the authenticated user by id",
      responses = {
        @ApiResponse(
            responseCode = "204",
            description = "Work time deleted successfully",
            content = @Content(schema = @Schema())),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "404", description = "Work time not found")
      })
  ResponseEntity<Void> deleteWorkTime(
      @Parameter(description = "ID of the work time entry", example = "12345", required = true)
          @PathVariable
          @NotBlank
          @Valid
          String id);
}