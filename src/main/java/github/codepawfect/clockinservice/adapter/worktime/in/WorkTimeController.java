package github.codepawfect.clockinservice.adapter.worktime.in;

import github.codepawfect.clockinservice.adapter.utils.JwtUtils;
import github.codepawfect.clockinservice.adapter.worktime.in.model.CreateWorkTimeRequest;
import github.codepawfect.clockinservice.domain.worktime.ports.in.CreateWorkTimePort;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

/**
 * WorkTimeController is a controller for handling work time requests.
 */
@RestController
@RequestMapping("api/worktime")
public class WorkTimeController {

    private final CreateWorkTimePort createWorkTimePort;
    private final JwtUtils jwtUtils;

    public WorkTimeController(CreateWorkTimePort createWorkTimePort, JwtUtils jwtUtils) {
        this.createWorkTimePort = createWorkTimePort;
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
        Long workTimeId = createWorkTimePort.createWorkTime(username, createWorkTimeRequest.date(), createWorkTimeRequest.hoursWorked());

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(workTimeId)
                .toUri();

        return ResponseEntity.created(location).build();
    }
}
