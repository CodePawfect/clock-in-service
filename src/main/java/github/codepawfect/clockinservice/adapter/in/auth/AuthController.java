package github.codepawfect.clockinservice.adapter.in.auth;

import github.codepawfect.clockinservice.adapter.in.auth.model.LoginRequest;
import github.codepawfect.clockinservice.adapter.in.auth.model.MeResponse;
import github.codepawfect.clockinservice.adapter.in.auth.model.RegisterRequest;
import github.codepawfect.clockinservice.application.in.auth.usecase.LoginUserUseCase;
import github.codepawfect.clockinservice.application.in.auth.usecase.LogoutUserUseCase;
import github.codepawfect.clockinservice.application.in.auth.usecase.RegisterUserUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

/** Controller for handling authentication requests. */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

  private final LoginUserUseCase loginUserUseCase;
  private final LogoutUserUseCase logoutUserUseCase;
  private final RegisterUserUseCase registerUserUseCase;

  /**
   * Authenticates a user with the given username and password.
   *
   * @param loginRequest the login request
   * @return the response entity containing the user's username and roles
   */
  @Operation(
      summary = "Authenticate a user",
      description =
          "Authenticates a user with the given username and password and sets the authentication cookie",
      responses = {
        @ApiResponse(responseCode = "200", description = "User authenticated successfully"),
        @ApiResponse(responseCode = "401", description = "Invalid credentials")
      })
  @PostMapping("/login")
  public ResponseEntity<Void> login(@RequestBody @Valid LoginRequest loginRequest) {
    LoginUserUseCase.LoginUserQueryDTO query =
        new LoginUserUseCase.LoginUserQueryDTO(loginRequest.username(), loginRequest.password());

    LoginUserUseCase.LoginUserResponseDTO response = loginUserUseCase.execute(query);

    return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, response.cookie().toString()).build();
  }

  /**
   * Registers a new user with the given username, password, and email.
   *
   * @param registerRequest the register request
   */
  @Operation(
      summary = "Register a user",
      description = "Registers a user with the given username and password")
  @PostMapping("/register")
  public ResponseEntity<Void> register(@RequestBody @Valid RegisterRequest registerRequest) {
    RegisterUserUseCase.RegisterUserCommandDTO command =
        new RegisterUserUseCase.RegisterUserCommandDTO(
            registerRequest.username(), registerRequest.password());

    registerUserUseCase.execute(command);

    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  /** Logs out the currently authenticated user. */
  @Operation(
      summary = "Logout a user",
      description = "Logs out the currently authenticated user by invalidating the JWT token")
  @PostMapping("/logout")
  public ResponseEntity<Void> logout() {
    LogoutUserUseCase.LogoutUserResponseDTO response = logoutUserUseCase.execute();

    return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, response.cookie().toString()).build();
  }

  @Operation(
      summary = "Information about the current authenticated user",
      description = "Get the username and roles for the current authenticated user",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "User is authenticated",
            content = @Content(schema = @Schema(implementation = MeResponse.class))),
        @ApiResponse(responseCode = "401", description = "Unauthorized")
      })
  @GetMapping("/me")
  public ResponseEntity<MeResponse> me(Authentication authentication) {

    String username = authentication.getName();
    List<String> roles =
        authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();

    MeResponse meResponse = new MeResponse(username, roles);

    return ResponseEntity.ok(meResponse);
  }
}
