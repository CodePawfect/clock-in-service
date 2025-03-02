package github.codepawfect.clockinservice.adapter.worktime.out.model;

import github.codepawfect.clockinservice.domain.worktime.model.WorkTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "worktimes")
public record WorkTimeDocument(@Id String id, String username, LocalDate date, Integer hoursWorked) {

    /**
     * Creates a new WorkTimeDocument.
     *
     * @param workTime the work time to create the document from.
     *
     * @return a new WorkTimeDocument.
     */
    public static WorkTimeDocument from(WorkTime workTime) {
        return new WorkTimeDocument(null, workTime.username(), workTime.date(), workTime.hoursWorked());
    }

    /**
     * Converts a WorkTimeDocument to a WorkTime.
     *
     * @param workTimeDocument the WorkTimeDocument to convert.
     *
     * @return a new WorkTime.
     */
    public static WorkTime toWorkTime(WorkTimeDocument workTimeDocument) {
        return new WorkTime(workTimeDocument.username(), workTimeDocument.date(), workTimeDocument.hoursWorked());
    }

}

