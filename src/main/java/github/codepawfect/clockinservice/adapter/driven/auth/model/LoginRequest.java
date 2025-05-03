package github.codepawfect.clockinservice.adapter.driven.auth.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

/** LoginRequest is a model class that represents the request body for the login endpoint. */
public record LoginRequest(
    @Schema(
            description = "Username of the user",
            example = "john.doe",
            requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank
        String username,
    @Schema(
            description = "Password of the user",
            example = "password",
            requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank
        String password) {}
