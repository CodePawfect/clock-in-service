package github.codepawfect.clockinservice.adapter.in.worktime.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * CreateWorkTimeRequest is a model class that represents the request body for the create work time
 * endpoint.
 */
@Schema(description = "Request to create a new work time entry")
public record CreateWorkTimeRequest(
    @Schema(
            description = "Date of the work time entry",
            example = "2023-10-15",
            requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull
        LocalDate date,
    @Schema(
            description = "Number of hours worked",
            example = "8",
            requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull
        @Min(1)
        @Max(12)
        Integer hoursWorked,
    @Schema(
            description = "Calendar week of the work time entry",
            example = "34",
            requiredMode = Schema.RequiredMode.REQUIRED)
        @Nullable
        String note) {}
