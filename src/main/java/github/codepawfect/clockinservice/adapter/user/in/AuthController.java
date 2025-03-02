package github.codepawfect.clockinservice.adapter.user.in;

import github.codepawfect.clockinservice.adapter.user.in.model.RegisterRequest;
import github.codepawfect.clockinservice.adapter.user.out.service.exception.UserAlreadyExistsException;
import github.codepawfect.clockinservice.adapter.user.out.service.model.AuthenticatedUserInformation;
import github.codepawfect.clockinservice.adapter.user.in.model.LoginRequest;
import github.codepawfect.clockinservice.adapter.user.in.model.LoginResponse;
import github.codepawfect.clockinservice.adapter.user.out.service.AuthenticationService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for handling authentication requests.
 */
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
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        AuthenticatedUserInformation response = authService.authenticate(
                loginRequest.username(),
                loginRequest.password());

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
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
        try {
            authService.register(
                    registerRequest.username(),
                    registerRequest.password());

            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (UserAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    /**
     * Logs out the currently authenticated user.
     *
     * @return the response entity containing the cookie to delete
     */
    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        ResponseCookie cookie = authService.logout();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .build();
    }
}

