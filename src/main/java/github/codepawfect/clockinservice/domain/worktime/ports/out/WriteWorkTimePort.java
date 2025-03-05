package github.codepawfect.clockinservice.domain.worktime.ports.out;

import github.codepawfect.clockinservice.domain.worktime.model.WorkTime;

/** PersistWorkTimePort is a port for persisting work time entries. */
public interface WriteWorkTimePort {

  /**
   * Persists a new work time entry.
   *
   * @param workTime the work time entry to persist
   * @return the ID of the created work time entry
   */
  String save(WorkTime workTime);
}
