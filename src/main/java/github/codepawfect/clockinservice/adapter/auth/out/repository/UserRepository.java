package github.codepawfect.clockinservice.adapter.auth.out.repository;

import github.codepawfect.clockinservice.adapter.auth.out.model.UserDocument;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/** Repository for handling user documents in the database. */
@Repository
public interface UserRepository extends MongoRepository<UserDocument, String> {
  Optional<UserDocument> findByUsername(String username);

  Boolean existsByUsername(String username);
}
