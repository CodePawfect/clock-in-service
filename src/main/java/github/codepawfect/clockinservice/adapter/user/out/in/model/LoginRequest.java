package github.codepawfect.clockinservice.adapter.user.out.in.model;

/**
 * LoginRequest is a model class that represents the request body for the login endpoint.
 */
public record LoginRequest(String username, String password) {}
