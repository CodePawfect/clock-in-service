package github.codepawfect.clockinservice.adapter.auth.out.service.model;

import java.util.List;
import org.springframework.http.ResponseCookie;

/**
 * AuthenticatedUserInformation is a model class that represents the response body for the login
 * endpoint.
 */
public record AuthenticatedUserInformation(
    String username, List<String> roles, ResponseCookie cookie) {}
