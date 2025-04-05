package github.codepawfect.clockinservice.adapter.worktime.in.model;

import java.util.List;

/**
 * GetWorkTimesResponse is a model class that represents the response body for the get work times
 * endpoint.
 */
public record GetWorkTimesResponse(List<WorkTimeDto> workTimes) {}
