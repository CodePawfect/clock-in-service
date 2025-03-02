package github.codepawfect.clockinservice.adapter.worktime.out.repository;

import github.codepawfect.clockinservice.adapter.worktime.out.model.WorkTimeDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkTimeRepository extends MongoRepository<WorkTimeDocument, String> {
}
