package github.codepawfect.clockinservice.application.auth;

import github.codepawfect.clockinservice.adapter.in.common.exception.UserAlreadyExistsException;
import github.codepawfect.clockinservice.application.auth.port.SaveUserPort;
import github.codepawfect.clockinservice.application.auth.port.UserExistsPort;
import github.codepawfect.clockinservice.domain.user.model.NewUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
@RequiredArgsConstructor
public class RegisterUseCase {

    private final PasswordEncoder passwordEncoder;
    private final SaveUserPort saveUserPort;
    private final UserExistsPort userExistsPort;

    /**
     * Registers a new user with the given username and password.
     *
     * @param username the username of the new user
     * @param password the password of the new user
     * @throws UserAlreadyExistsException if a user with the given username already exists
     */
    public void execute(String username, String password) throws UserAlreadyExistsException {
        NewUser newUser =
                new NewUser(username, passwordEncoder.encode(password), Collections.singletonList("USER"));

       var exists = userExistsPort.existsByUsername(username);

       if(exists) {
              throw new UserAlreadyExistsException("User with username " + username + " already exists.");
       }

       saveUserPort.save(newUser);
    }
}


