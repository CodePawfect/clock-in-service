package github.codepawfect.clockinservice.application.in.worktime.usecase;

public interface DeleteWorkTimeUseCase {

  /**
   * Deletes a work time entry by its ID.
   *
   * @param command the command containing the ID of the work time to delete
   */
  void execute(DeleteWorkTimeCommandDTO command);

  record DeleteWorkTimeCommandDTO(String id) {}
}
