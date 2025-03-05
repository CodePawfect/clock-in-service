package github.codepawfect.clockinservice.migration;

import io.mongock.api.annotations.ChangeUnit;
import io.mongock.api.annotations.Execution;
import io.mongock.api.annotations.RollbackExecution;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.data.mongodb.core.index.IndexDefinition;
import org.springframework.data.mongodb.core.index.IndexOperations;

/** Initializes the users collection in the database. */
@ChangeUnit(id = "users-collection-initialization", order = "001", author = "codepawfect")
public class V001__UsersCollectionInitialization {

  /**
   * Creates the users collection and indexes.
   *
   * @param mongoTemplate The MongoTemplate to use for database operations.
   */
  @Execution
  public void execution(MongoTemplate mongoTemplate) {
    if (!mongoTemplate.collectionExists("users")) {
      mongoTemplate.createCollection("users");

      IndexOperations indexOps = mongoTemplate.indexOps("users");

      IndexDefinition usernameIndex = new Index().on("username", Sort.Direction.ASC).unique();

      indexOps.ensureIndex(usernameIndex);
    }
  }

  /**
   * Drops the users collection.
   *
   * @param mongoTemplate The MongoTemplate to use for database operations.
   */
  @RollbackExecution
  public void rollbackExecution(MongoTemplate mongoTemplate) {
    mongoTemplate.dropCollection("users");
  }
}
