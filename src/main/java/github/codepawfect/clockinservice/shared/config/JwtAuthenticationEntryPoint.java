package github.codepawfect.clockinservice.shared.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

/** Handles authentication errors and sends an unauthorized response. */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

  /**
   * Sends an unauthorized response when an authentication error occurs.
   *
   * @param request The request that caused the authentication error.
   * @param response The response to send the error to.
   * @param authException The authentication error that occurred.
   * @throws IOException If an I/O error occurs.
   */
  @Override
  public void commence(
      HttpServletRequest request,
      HttpServletResponse response,
      AuthenticationException authException)
      throws IOException {

    response.setContentType("application/json");
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

    PrintWriter writer = response.getWriter();
    writer.println(
        "{\"error\": \"Nicht autorisiert\", \"message\": \"" + authException.getMessage() + "\"}");
  }
}
