package github.codepawfect.clockinservice.migration;

import github.codepawfect.clockinservice.adapter.user.out.model.UserDocument;
import io.mongock.api.annotations.ChangeUnit;
import io.mongock.api.annotations.Execution;
import io.mongock.api.annotations.RollbackExecution;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;

/**
 * Initializes the admin user in the database.
 */
@ChangeUnit(id = "admin-user-initialization", order = "002", author = "codepawfect")
public class V002__AdminUserInitialization {

    private final Environment environment;

    public V002__AdminUserInitialization(Environment environment) {
        this.environment = environment;
    }

    /**
     * Creates the admin user if it does not already exist.
     *
     * @param mongoTemplate   The MongoTemplate to use for database operations.
     * @param passwordEncoder The PasswordEncoder to use for encoding the password.
     */
    @Execution
    public void execution(MongoTemplate mongoTemplate, PasswordEncoder passwordEncoder) {
        String adminUsername = environment.getProperty("admin.initial.username");
        String adminPassword = environment.getProperty("admin.initial.password");

        if (adminPassword != null && !adminPassword.isEmpty() &&
                !mongoTemplate.exists(Query.query(Criteria.where("username")
                        .is(adminUsername)), "users")) {

            UserDocument adminUser = new UserDocument();
            adminUser.setUsername(adminUsername);
            adminUser.setPassword(passwordEncoder.encode(adminPassword));
            adminUser.setRoles(Collections.singletonList("ADMIN"));

            mongoTemplate.save(adminUser, "users");
        }
    }

    @RollbackExecution
    public void rollbackExecution(MongoTemplate mongoTemplate) {
        // Empty because we don't want to delete admin user on rollback
    }
}

