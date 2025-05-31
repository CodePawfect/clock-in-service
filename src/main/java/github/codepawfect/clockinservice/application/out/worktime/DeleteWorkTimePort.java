package github.codepawfect.clockinservice.application.out.worktime;

import org.springframework.stereotype.Component;

@Component
public interface DeleteWorkTimePort {

  /**
   * Deletes a work time entry.
   *
   * @param id the ID of the work time entry to delete
   */
  void deleteWorkTime(String id);
}
