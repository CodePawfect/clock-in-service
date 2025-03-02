package github.codepawfect.clockinservice.domain.worktime.service;

import github.codepawfect.clockinservice.domain.worktime.ports.in.CreateWorkTimePort;
import github.codepawfect.clockinservice.domain.worktime.ports.out.PersistWorkTimePort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

/**
 * WorkTimeService is a service for handling work time operations.
 */
@Service
public class WorkTimeService implements CreateWorkTimePort {

    private final PersistWorkTimePort persistWorkTimePort;

    public WorkTimeService(PersistWorkTimePort persistWorkTimePort) {
        this.persistWorkTimePort = persistWorkTimePort;
    }

    /** {@inheritDoc} */
    @Override
    public Long createWorkTime(String username, LocalDate date, Integer hoursWorked) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
