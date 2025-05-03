package github.codepawfect.clockinservice.application.driven.worktime;

import github.codepawfect.clockinservice.application.driving.worktime.DeleteWorkTimePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeleteWorkTimeUseCase {

    private final DeleteWorkTimePort deleteWorkTimePort;

    /**
     * Deletes a work time entry.
     *
     * @param id the ID of the work time entry to delete
     */
    public void execute(String id) {
        deleteWorkTimePort.deleteWorkTime(id);
    }
}
