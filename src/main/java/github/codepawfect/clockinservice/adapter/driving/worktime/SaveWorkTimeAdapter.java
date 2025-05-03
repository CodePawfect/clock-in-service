package github.codepawfect.clockinservice.adapter.driving.worktime;

import github.codepawfect.clockinservice.adapter.driving.worktime.model.WorkTimeDocument;
import github.codepawfect.clockinservice.adapter.driving.worktime.repository.WorkTimeRepository;
import github.codepawfect.clockinservice.application.driving.worktime.SaveWorkTimePort;
import github.codepawfect.clockinservice.domain.worktime.model.WorkTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SaveWorkTimeAdapter implements SaveWorkTimePort {

    private final WorkTimeRepository workTimeRepository;

    /** {@inheritDoc} */
    @Override
    public String save(WorkTime workTime) {
        return workTimeRepository.save(WorkTimeDocument.from(workTime)).id();
    }
}
