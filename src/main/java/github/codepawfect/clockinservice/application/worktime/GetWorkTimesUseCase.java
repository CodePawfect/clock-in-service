package github.codepawfect.clockinservice.application.worktime;

import github.codepawfect.clockinservice.application.worktime.port.FindWorkTimesPort;
import github.codepawfect.clockinservice.domain.worktime.model.WorkTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GetWorkTimesUseCase {

    private final FindWorkTimesPort findWorkTimesPort;

    /**
     * Reads work times for a user in a specific calender week and year.
     *
     * @param username the username of the user
     * @param calenderWeek the calender week
     * @param year the year
     * @return a list of work times
     */
    public List<WorkTime> execute(String username, int calenderWeek, int year) {
        return findWorkTimesPort.getAll(username, calenderWeek, year);
    }
}
