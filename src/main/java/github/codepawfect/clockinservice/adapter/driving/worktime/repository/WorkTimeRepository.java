package github.codepawfect.clockinservice.adapter.driving.worktime.repository;

import github.codepawfect.clockinservice.adapter.driving.worktime.model.WorkTimeDocument;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/** WorkTimeRepository is a repository interface for the WorkTimeDocument. */
@Repository
public interface WorkTimeRepository extends MongoRepository<WorkTimeDocument, String> {

  List<WorkTimeDocument> findByUsernameAndCalenderWeekAndYear(
      String username, int calenderWeek, int year);
}
