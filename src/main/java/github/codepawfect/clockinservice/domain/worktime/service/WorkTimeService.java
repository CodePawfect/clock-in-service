package github.codepawfect.clockinservice.domain.worktime.service;

import github.codepawfect.clockinservice.domain.worktime.model.WorkTime;
import github.codepawfect.clockinservice.domain.worktime.ports.in.CreateWorkTimePort;
import github.codepawfect.clockinservice.domain.worktime.ports.in.GetWorkTimesPort;
import github.codepawfect.clockinservice.domain.worktime.ports.out.ReadWorkTimePort;
import github.codepawfect.clockinservice.domain.worktime.ports.out.WriteWorkTimePort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * WorkTimeService is a service for handling work time operations.
 */
@Service
public class WorkTimeService implements CreateWorkTimePort, GetWorkTimesPort {

    private final WriteWorkTimePort WriteWorkTimePort;
    private final ReadWorkTimePort readWorkTimePort;

    public WorkTimeService(WriteWorkTimePort writeWorkTimePort, ReadWorkTimePort readWorkTimePort) {
        this.WriteWorkTimePort = writeWorkTimePort;
        this.readWorkTimePort = readWorkTimePort;
    }

    /** {@inheritDoc} */
    @Override
    public String createWorkTime(String username, LocalDate date, Integer hoursWorked) {
       return WriteWorkTimePort.save(new WorkTime(username, date, hoursWorked));
    }

    /** {@inheritDoc} */
    @Override
    public List<WorkTime> getWorkTimes(String username, int calenderWeek, int year) {
        return readWorkTimePort.find(username, calenderWeek, year);
    }
}
