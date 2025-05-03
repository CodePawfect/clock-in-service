package github.codepawfect.clockinservice.adapter.driven.auth.model;

import java.util.List;

/** MeResponse is a model class that represents the response body for the me endpoint. */
public record MeResponse(String username, List<String> roles) {}
