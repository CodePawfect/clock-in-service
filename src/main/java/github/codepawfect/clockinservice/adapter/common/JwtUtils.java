package github.codepawfect.clockinservice.adapter.common;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.crypto.SecretKey;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

/**
 * JwtUtils is a utility class that provides methods for generating, validating, and extracting JWT
 * tokens.
 */
@Component
public class JwtUtils {

  private final JwtProperties jwtProperties;

  public JwtUtils(JwtProperties jwtProperties) {
    this.jwtProperties = jwtProperties;
  }

  /**
   * Generates a JWT token for the given user details.
   *
   * @param userDetails the authenticated user details
   * @return a signed JWT token string
   */
  private String generateToken(UserDetails userDetails) {
    Map<String, Object> claims = new HashMap<>();
    List<String> roles =
            userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());

    claims.put("roles", roles);

    return createToken(claims, userDetails.getUsername());
  }

  /**
   * Creates a JWT token with the specified claims and subject.
   *
   * @param claims a map of claims to include in the token
   * @param subject the subject of the token (usually the username)
   * @return a signed JWT token string
   */
  private String createToken(Map<String, Object> claims, String subject) {
    Date now = new Date();
    Date expiry = new Date(now.getTime() + jwtProperties.getExpiration());
    return Jwts.builder()
        .claims(claims)
        .subject(subject)
        .issuedAt(now)
        .expiration(expiry)
        .signWith(getVerificationKey())
        .compact();
  }

  /**
   * Retrieves the verification key used to sign the JWT token.
   *
   * @return the verification key
   */
  private SecretKey getVerificationKey() {
    byte[] keyBytes = jwtProperties.getSecret().getBytes(StandardCharsets.UTF_8);
    if (keyBytes.length < 32) {
      throw new IllegalArgumentException("The JWT secret key must be at least 32 bytes long.");
    }
    return Keys.hmacShaKeyFor(keyBytes);
  }

  /**
   * Extracts the username from the JWT token.
   *
   * @param token the JWT token
   * @return the username
   */
  public String extractUsername(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  /**
   * Extracts the expiration date from the JWT token.
   *
   * @param token the JWT token
   * @return the expiration date
   */
  private Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  /**
   * Extracts a specific claim from the JWT token using the provided claims resolver function.
   *
   * @param token the JWT token
   * @param claimsResolver a function to resolve the claim
   * @return the extracted claim value
   * @param <T> the type of the claim value
   */
  private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  /**
   * Extracts all claims from the JWT token.
   *
   * @param token the JWT token
   * @return the claims contained in the token
   */
  private Claims extractAllClaims(String token) {
    return Jwts.parser()
        .verifyWith(getVerificationKey())
        .build()
        .parseSignedClaims(token)
        .getPayload();
  }

  /**
   * Checks if the token is expired by comparing the expiration date with the current date.
   *
   * @param token the JWT token
   * @return true if the token is expired, false otherwise
   */
  private boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  /**
   * Validates the token by checking the username and expiration
   *
   * @param token authentication token
   * @return true if the user is authenticated and the token is not expired. False if the token is
   *     expired
   */
  public boolean validateToken(String token) {
    extractUsername(token);
    return !isTokenExpired(token);
  }

  /**
   * validates the token and returns the username
   *
   * @param token authentication token
   * @return username if the token is valid, null if the token is expired
   */
  public String getUsernameByToken(String token) {
    String username = extractUsername(token);
    return !isTokenExpired(token) ? username : null;
  }

  /**
   * Retrieves the JWT token value from cookies present in the HTTP request.
   *
   * @param request the HTTP request
   * @return the JWT token value, or null if not found
   */
  public String getJwtFromCookies(HttpServletRequest request) {
    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
      for (Cookie cookie : cookies) {
        if (jwtProperties.getCookieName().equals(cookie.getName())) {
          return cookie.getValue();
        }
      }
    }
    return null;
  }

  /**
   * Generates a JWT cookie with the specified user details.
   *
   * @param userDetails the authenticated user details
   * @return a ResponseCookie containing the JWT token
   */
  public ResponseCookie generateJwtCookie(UserDetails userDetails) {
    String jwt = generateToken(userDetails);

    return ResponseCookie.from(jwtProperties.getCookieName(), jwt)
        .path("/")
        .maxAge(24 * 60 * 60) // 1 day in seconds
        .httpOnly(true) // prevents JavaScript access
        .secure(jwtProperties.isSecured()) // only over HTTPS
        .sameSite("Strict") // mitigates CSRF
        .build();
  }

  /**
   * Generates a clean JWT cookie to delete the existing JWT token.
   *
   * @return a ResponseCookie with an empty value and max age of 0
   */
  public ResponseCookie invalidateAuthCookie() {
    return ResponseCookie.from(jwtProperties.getCookieName(), "")
        .path("/")
        .maxAge(0)
        .httpOnly(true)
        .secure(jwtProperties.isSecured())
        .sameSite("Strict")
        .build();
  }
}
