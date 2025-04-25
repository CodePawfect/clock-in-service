package github.codepawfect.clockinservice.application.auth;

import github.codepawfect.clockinservice.adapter.in.common.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LogoutUseCase {

    private final JwtUtils jwtUtils;

    /**
     * Logs out the user by invalidating the authentication cookie.
     *
     * @return a ResponseCookie indicating the logout status
     */
    public ResponseCookie execute() {
        return jwtUtils.invalidateAuthCookie();
    }
}
