package github.codepawfect.clockinservice.application.worktime.port;

import github.codepawfect.clockinservice.domain.worktime.model.WorkTime;
import org.springframework.stereotype.Component;

@Component
public interface SaveWorkTimePort {

    /**
     * saves a new work time entry.
     *
     * @param workTime the work time entry to persist
     * @return the ID of the created work time entry
     */
     String save(WorkTime workTime);
}
