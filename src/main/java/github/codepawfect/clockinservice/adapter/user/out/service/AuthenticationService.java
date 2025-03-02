package github.codepawfect.clockinservice.adapter.user.out.service;

import github.codepawfect.clockinservice.adapter.user.out.service.model.AuthenticatedUserInformation;
import github.codepawfect.clockinservice.adapter.utils.JwtUtils;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * AuthenticationService is a service class that handles authentication and logout operations.
 */
@Service
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    public AuthenticationService(AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    /**
     * Authenticates a user with the given username and password.
     *
     * @param username the username
     * @param password the password
     * @return the authenticated user information
     */
    public AuthenticatedUserInformation authenticate(String username, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);

        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return new AuthenticatedUserInformation(userDetails.getUsername(), roles, jwtCookie);
    }

    /**
     * Logs out the currently authenticated user.
     *
     * @return the cookie to delete
     */
    public ResponseCookie logout() {
        return jwtUtils.getCleanJwtCookie();
    }
}

