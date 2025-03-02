package github.codepawfect.clockinservice.adapter.user.out.service.model;

import org.springframework.http.ResponseCookie;

import java.util.List;

/**
 * AuthenticatedUserInformation is a model class that represents the response body for the login endpoint.
 */
public record AuthenticatedUserInformation(String username, List<String> roles, ResponseCookie cookie) {
}


