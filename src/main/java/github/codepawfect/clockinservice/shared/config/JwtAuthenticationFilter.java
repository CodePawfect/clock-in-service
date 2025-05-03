package github.codepawfect.clockinservice.shared.config;

import github.codepawfect.clockinservice.shared.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/** Filter for authenticating requests using JWT tokens. */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final JwtUtils jwtUtils;
  private final UserDetailsService userDetailsService;

  public JwtAuthenticationFilter(JwtUtils jwtUtils, UserDetailsService userDetailsService) {
    this.jwtUtils = jwtUtils;
    this.userDetailsService = userDetailsService;
  }

  /**
   * Authenticates requests using JWT tokens.
   *
   * @param request The request to authenticate.
   * @param response The response to send the authentication to.
   * @param filterChain The filter chain to continue processing the request.
   * @throws ServletException If a servlet error occurs.
   * @throws IOException If an I/O error occurs.
   */
  @Override
  protected void doFilterInternal(
      @NonNull HttpServletRequest request,
      @NonNull HttpServletResponse response,
      @NonNull FilterChain filterChain)
      throws ServletException, IOException {

    try {
      String jwt = jwtUtils.getJwtFromCookies(request);
      if (jwt != null && jwtUtils.validateToken(jwt)) {
        String username = jwtUtils.extractUsername(jwt);

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        UsernamePasswordAuthenticationToken authentication =
            new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());

        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
      }
    } catch (Exception e) {
      logger.error("Cannot set user authentication: {}", e);
    }

    filterChain.doFilter(request, response);
  }
}
