package github.codepawfect.clockinservice.domain.worktime.ports.in;

/** DeleteWorkTimePort is a port for deleting work time entries. */
public interface DeleteWorkTimeUseCasePort {

  /**
   * Deletes a work time entry.
   *
   * @param id the ID of the work time entry to delete
   */
  void deleteWorkTime(String id);
}
