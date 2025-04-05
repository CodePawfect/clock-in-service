package github.codepawfect.clockinservice.adapter.auth.in.model;

import java.util.List;

/** LoginResponse is a model class that represents the response body for the login endpoint. */
public record LoginResponse(String username, List<String> roles) {}
