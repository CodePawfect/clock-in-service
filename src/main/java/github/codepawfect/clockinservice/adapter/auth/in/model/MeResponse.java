package github.codepawfect.clockinservice.adapter.auth.in.model;

import java.util.List;

/** MeResponse is a model class that represents the response body for the me endpoint. */
public record MeResponse(String username, List<String> roles) {}
