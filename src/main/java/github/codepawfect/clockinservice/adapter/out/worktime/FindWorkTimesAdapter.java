package github.codepawfect.clockinservice.adapter.out.worktime;

import github.codepawfect.clockinservice.adapter.out.worktime.model.WorkTimeDocument;
import github.codepawfect.clockinservice.adapter.out.worktime.repository.WorkTimeRepository;
import github.codepawfect.clockinservice.application.out.worktime.FindWorkTimesPort;
import github.codepawfect.clockinservice.domain.worktime.model.WorkTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FindWorkTimesAdapter implements FindWorkTimesPort {

    private final WorkTimeRepository workTimeRepository;

    @Override
    public List<WorkTime> getAll(String username, int calenderWeek, int year) {
        return workTimeRepository
        .findByUsernameAndCalenderWeekAndYear(username, calenderWeek, year)
        .stream()
        .map(WorkTimeDocument::toWorkTime)
        .toList();
    }
}
