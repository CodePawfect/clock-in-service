package github.codepawfect.clockinservice.application.driven.worktime;

import github.codepawfect.clockinservice.application.driving.worktime.SaveWorkTimePort;
import github.codepawfect.clockinservice.shared.DateUtils;
import github.codepawfect.clockinservice.domain.worktime.model.WorkTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class CreateWorkTimeUseCase {

    private final SaveWorkTimePort saveWorkTimePort;

    /**
     * Creates a new work time entry.
     *
     * @param username the username of the user
     * @param date the date of the work time entry
     * @param hoursWorked the hours worked
     * @param note the note for the work time entry
     * @return the ID of the created work time entry
     */
    public String execute(String username, LocalDate date, Integer hoursWorked, String note) {
        var newWorktime = new WorkTime(
                null,
                username,
                date,
                hoursWorked,
                date.getYear(),
                DateUtils.getCalenderWeek(date),
                note);

        return saveWorkTimePort.save(newWorktime);
    }
}
