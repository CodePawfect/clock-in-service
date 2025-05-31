package github.codepawfect.clockinservice.adapter.in.worktime.model;

import java.util.List;

/**
 * GetWorkTimesResponse is a model class that represents the response body for the get work times
 * endpoint.
 */
public record GetWorkTimesResponse(List<WorkTimeResource> workTimes) {}
