package github.codepawfect.clockinservice.domain.user.service;

import github.codepawfect.clockinservice.domain.user.model.NewUser;
import github.codepawfect.clockinservice.domain.user.model.User;
import github.codepawfect.clockinservice.domain.user.ports.in.CreateUserPort;
import github.codepawfect.clockinservice.domain.user.ports.in.GetUserPort;
import github.codepawfect.clockinservice.domain.user.ports.out.ReadUserFromDatabasePort;
import github.codepawfect.clockinservice.domain.user.ports.out.WriteUserToDatabasePort;
import org.springframework.stereotype.Service;

/**
 * UserService is a service for managing users.
 * It implements the GetUserPort and CreateUserPort interfaces.
 */
@Service
public class UserService implements GetUserPort, CreateUserPort {

    private final ReadUserFromDatabasePort readUserFromDatabasePort;
    private final WriteUserToDatabasePort writeUserToDatabasePort;

    public UserService(ReadUserFromDatabasePort readUserFromDatabasePort, WriteUserToDatabasePort writeUserToDatabasePort) {
        this.readUserFromDatabasePort = readUserFromDatabasePort;
        this.writeUserToDatabasePort = writeUserToDatabasePort;
    }

    /** {@inheritDoc} */
    @Override
    public void createUser(NewUser newUser) {
        writeUserToDatabasePort.save(newUser);
    }

    /** {@inheritDoc} */
    @Override
    public User getUserByUsername(String username) {
        return readUserFromDatabasePort.findByUsername(username);
    }

    /** {@inheritDoc} */
    @Override
    public User getUserById(String id) {
        return readUserFromDatabasePort.findById(id);
    }

    /** {@inheritDoc} */
    @Override
    public boolean existsByUsername(String username) {
        return readUserFromDatabasePort.existsByUsername(username);
    }
}
