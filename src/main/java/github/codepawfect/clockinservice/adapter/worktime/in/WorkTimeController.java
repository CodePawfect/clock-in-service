package github.codepawfect.clockinservice.adapter.worktime.in;

import github.codepawfect.clockinservice.adapter.utils.JwtUtils;
import github.codepawfect.clockinservice.adapter.worktime.in.model.CreateWorkTimeRequest;
import github.codepawfect.clockinservice.adapter.worktime.in.model.GetWorkTimesResponse;
import github.codepawfect.clockinservice.domain.worktime.model.WorkTime;
import github.codepawfect.clockinservice.domain.worktime.ports.in.CreateWorkTimePort;
import github.codepawfect.clockinservice.domain.worktime.ports.in.GetWorkTimesPort;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

/**
 * WorkTimeController is a controller for handling work time requests.
 */
@RestController
@RequestMapping("/api/worktimes")
public class WorkTimeController {

    private final CreateWorkTimePort createWorkTimePort;
    private final GetWorkTimesPort getWorkTimesPort;
    private final JwtUtils jwtUtils;

    public WorkTimeController(CreateWorkTimePort createWorkTimePort, GetWorkTimesPort getWorkTimesPort, JwtUtils jwtUtils) {
        this.createWorkTimePort = createWorkTimePort;
        this.getWorkTimesPort = getWorkTimesPort;
        this.jwtUtils = jwtUtils;
    }

    /**
     * Creates a new work time entry.
     *
     * @param createWorkTimeRequest the create work time request
     */
    @PostMapping()
    public ResponseEntity<URI> createWorkTime(@RequestBody CreateWorkTimeRequest createWorkTimeRequest,
                                              HttpServletRequest request) {
        String username = jwtUtils.extractUsername(jwtUtils.getJwtFromCookies(request));
        String workTimeId = createWorkTimePort.createWorkTime(username, createWorkTimeRequest.date(), createWorkTimeRequest.hoursWorked());

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(workTimeId)
                .toUri();

        return ResponseEntity.created(location).build();
    }

    /**
     * Gets work times for a specific calendar week and year.
     *
     * @param calenderWeek the calendar week
     * @param year the year
     */
    @GetMapping("/{calenderWeek}/{year}")
    public ResponseEntity<GetWorkTimesResponse> getWorkTimes(@PathVariable int calenderWeek, @PathVariable int year, HttpServletRequest request) {
        String username = jwtUtils.extractUsername(jwtUtils.getJwtFromCookies(request));

        List<WorkTime> workTimes = getWorkTimesPort.getWorkTimes(username, calenderWeek, year);
        GetWorkTimesResponse getWorkTimesResponse = new GetWorkTimesResponse(workTimes);

        return ResponseEntity.ok(getWorkTimesResponse);
    }
}
