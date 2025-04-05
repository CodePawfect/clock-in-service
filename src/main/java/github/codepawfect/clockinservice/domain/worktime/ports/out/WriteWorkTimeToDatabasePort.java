package github.codepawfect.clockinservice.domain.worktime.ports.out;

import github.codepawfect.clockinservice.domain.worktime.model.WorkTime;

/** WriteWorkTimeToDatabasePort is a port for persisting work time entries. */
public interface WriteWorkTimeToDatabasePort {

  /**
   * Persists a new work time entry.
   *
   * @param workTime the work time entry to persist
   * @return the ID of the created work time entry
   */
  String save(WorkTime workTime);

  /**
   * Deletes an existing work time entry.
   *
   * @param id the ID of the work time entry to delete
   */
  void delete(String id);
}
