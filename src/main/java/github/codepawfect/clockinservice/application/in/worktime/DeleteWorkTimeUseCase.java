package github.codepawfect.clockinservice.application.in.worktime;

public interface DeleteWorkTimeUseCase {

  /**
   * Deletes a work time entry.
   *
   * @param id the ID of the work time entry to delete
   */
  void execute(String id);
}
