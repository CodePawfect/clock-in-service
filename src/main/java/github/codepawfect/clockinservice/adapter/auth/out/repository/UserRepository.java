package github.codepawfect.clockinservice.adapter.auth.out.repository;

import github.codepawfect.clockinservice.adapter.auth.out.model.UserDocument;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/** Repository for handling user documents in the database. */
@Repository
public interface UserRepository extends MongoRepository<UserDocument, String> {

  /**
   * Finds a user document by its username.
   *
   * @param username the username of the user
   * @return an Optional containing the user document if found, or empty if not found
   */
  Optional<UserDocument> findByUsername(String username);

  /**
   * Checks if a user document exists by its username.
   *
   * @param username the username of the user
   * @return true if the user document exists, false otherwise
   */
  Boolean existsByUsername(String username);
}
