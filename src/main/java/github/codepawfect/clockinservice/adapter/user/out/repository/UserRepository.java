package github.codepawfect.clockinservice.adapter.user.out.repository;

import github.codepawfect.clockinservice.adapter.user.out.model.UserDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

/**
 * Repository for handling user documents in the database.
 */
public interface UserRepository extends MongoRepository<UserDocument, String> {
    Optional<UserDocument> findByUsername(String username);
}

