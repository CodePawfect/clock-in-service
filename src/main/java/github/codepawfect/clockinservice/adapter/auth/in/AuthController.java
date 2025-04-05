package github.codepawfect.clockinservice.adapter.auth.in;

import github.codepawfect.clockinservice.adapter.auth.in.model.LoginRequest;
import github.codepawfect.clockinservice.adapter.auth.in.model.LoginResponse;
import github.codepawfect.clockinservice.adapter.auth.in.model.MeResponse;
import github.codepawfect.clockinservice.adapter.auth.in.model.RegisterRequest;
import github.codepawfect.clockinservice.application.service.AuthenticationService;
import github.codepawfect.clockinservice.adapter.auth.out.service.model.AuthenticatedUserInformation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

/** Controller for handling authentication requests. */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

  private final AuthenticationService authService;

  public AuthController(AuthenticationService authService) {
    this.authService = authService;
  }

  /**
   * Authenticates a user with the given username and password.
   *
   * @param loginRequest the login request
   * @return the response entity containing the user's username and roles
   */
  @Operation(
      summary = "Authenticate a user",
      description = "Authenticates a user with the given username and password",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "User authenticated successfully",
            content = @Content(schema = @Schema(implementation = LoginResponse.class))),
        @ApiResponse(responseCode = "401", description = "Invalid credentials")
      })
  @PostMapping("/login")
  public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest loginRequest) {
    AuthenticatedUserInformation response =
        authService.login(loginRequest.username(), loginRequest.password());

    return ResponseEntity.ok()
        .header(HttpHeaders.SET_COOKIE, response.cookie().toString())
        .body(new LoginResponse(response.username(), response.roles()));
  }

  /**
   * Registers a new user with the given username, password, and email.
   *
   * @param registerRequest the register request
   * @return the response entity containing a message
   */
  @Operation(
      summary = "Register a user",
      description = "Registers a user with the given username and password",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "User registered successfully",
            content = @Content(schema = @Schema(implementation = LoginResponse.class)))
      })
  @PostMapping("/register")
  public ResponseEntity<?> register(@RequestBody @Valid RegisterRequest registerRequest) {
    authService.register(registerRequest.username(), registerRequest.password());

    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  /**
   * Logs out the currently authenticated user.
   *
   * @return the response entity containing the cookie to delete
   */
  @Operation(
      summary = "Logout a user",
      description = "Logs out the currently authenticated user",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "User logged out successfully",
            content = @Content(schema = @Schema(implementation = LoginResponse.class)))
      })
  @PostMapping("/logout")
  public ResponseEntity<Void> logout() {
    ResponseCookie cookie = authService.logout();

    return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString()).build();
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
