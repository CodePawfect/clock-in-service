package github.codepawfect.clockinservice.adapter.worktime.out;

import github.codepawfect.clockinservice.domain.worktime.ports.out.PersistWorkTimePort;
import org.springframework.stereotype.Component;

/**
 * PersistWorkTimeAdapter is an adapter for persisting work time entries.
 */
@Component
public class PersistWorkTimeAdapter implements PersistWorkTimePort {

    /**
     * {@inheritDoc}
     */
    @Override
    public String createWorkTime(String username, String date, Integer hoursWorked) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
