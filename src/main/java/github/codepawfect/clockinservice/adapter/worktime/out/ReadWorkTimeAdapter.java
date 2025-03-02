package github.codepawfect.clockinservice.adapter.worktime.out;

import github.codepawfect.clockinservice.adapter.worktime.out.model.WorkTimeDocument;
import github.codepawfect.clockinservice.adapter.worktime.out.repository.WorkTimeRepository;
import github.codepawfect.clockinservice.domain.worktime.model.WorkTime;
import github.codepawfect.clockinservice.domain.worktime.ports.out.ReadWorkTimePort;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * ReadWorkTimeAdapter is an adapter class that implements the ReadWorkTimePort interface.
 */
@Component
public class ReadWorkTimeAdapter implements ReadWorkTimePort {

    private final WorkTimeRepository workTimeRepository;

    public ReadWorkTimeAdapter(WorkTimeRepository workTimeRepository) {
        this.workTimeRepository = workTimeRepository;
    }

    /** {@inheritDoc} */
    @Override
    public List<WorkTime> find(String username, int calenderWeek, int year) {
        return workTimeRepository.findByUsernameAndCalenderWeekAndYear(username, calenderWeek, year)
                .stream()
                .map(WorkTimeDocument::toWorkTime)
                .toList();
    }
}
