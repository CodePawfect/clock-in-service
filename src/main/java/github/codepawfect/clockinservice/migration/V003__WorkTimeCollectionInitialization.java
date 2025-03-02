package github.codepawfect.clockinservice.migration;

import io.mongock.api.annotations.ChangeUnit;
import io.mongock.api.annotations.Execution;
import io.mongock.api.annotations.RollbackExecution;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.data.mongodb.core.index.IndexDefinition;
import org.springframework.data.mongodb.core.index.IndexOperations;

/**
 * Initializes the work time collection in the database.
 */
@ChangeUnit(id = "work-time-collection-initialization", order = "003", author = "codepawfect")
public class V003__WorkTimeCollectionInitialization {

    /**
     * Creates the users collection and indexes.
     *
     * @param mongoTemplate The MongoTemplate to use for database operations.
     */
    @Execution
    public void execution(MongoTemplate mongoTemplate) {
        if (!mongoTemplate.collectionExists("worktime")) {
            mongoTemplate.createCollection("worktime");

            IndexOperations indexOps = mongoTemplate.indexOps("worktime");

            IndexDefinition username_calenderWeek_year_index = new Index()
                    .on("username", Sort.Direction.ASC)
                    .on("calendarWeek", Sort.Direction.ASC)
                    .on("year", Sort.Direction.ASC);

            indexOps.ensureIndex(username_calenderWeek_year_index);
        }
    }

    /**
     * Drops the worktime collection.
     *
     * @param mongoTemplate The MongoTemplate to use for database operations.
     */
    @RollbackExecution
    public void rollbackExecution(MongoTemplate mongoTemplate) {
        mongoTemplate.dropCollection("worktime");
    }
}

