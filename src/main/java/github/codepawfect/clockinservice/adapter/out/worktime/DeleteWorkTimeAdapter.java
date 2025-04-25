package github.codepawfect.clockinservice.adapter.out.worktime;

import github.codepawfect.clockinservice.adapter.out.worktime.repository.WorkTimeRepository;
import github.codepawfect.clockinservice.application.worktime.port.DeleteWorkTimePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeleteWorkTimeAdapter implements DeleteWorkTimePort {

    private final WorkTimeRepository workTimeRepository;

    /** {@inheritDoc} */
    @Override
    public void deleteWorkTime(String id) {
        workTimeRepository.deleteById(id);
    }
}
