package github.codepawfect.clockinservice.adapter.worktime.out.model;

import github.codepawfect.clockinservice.domain.worktime.model.WorkTime;
import java.time.LocalDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "worktimes")
public record WorkTimeDocument(
    @Id String id,
    String username,
    LocalDate date,
    Integer year,
    Integer calenderWeek,
    Integer hoursWorked,
    String note) {

  /**
   * Creates a new WorkTimeDocument.
   *
   * @param workTime the work time to create the document from.
   * @return a new WorkTimeDocument.
   */
  public static WorkTimeDocument from(WorkTime workTime) {
    return new WorkTimeDocument(
        null,
        workTime.username(),
        workTime.date(),
        workTime.year(),
        workTime.calenderWeek(),
        workTime.hoursWorked(),
        workTime.note());
  }

  /**
   * Converts a WorkTimeDocument to a WorkTime.
   *
   * @param workTimeDocument the WorkTimeDocument to convert.
   * @return a new WorkTime.
   */
  public static WorkTime toWorkTime(WorkTimeDocument workTimeDocument) {
    return new WorkTime(
        workTimeDocument.id,
        workTimeDocument.username(),
        workTimeDocument.date(),
        workTimeDocument.hoursWorked(),
        workTimeDocument.year(),
        workTimeDocument.calenderWeek(),
        workTimeDocument.note());
  }
}
