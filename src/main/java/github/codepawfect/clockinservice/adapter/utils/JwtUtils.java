package github.codepawfect.clockinservice.adapter.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import javax.crypto.SecretKey;
import org.springframework.http.ResponseCookie;
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
  public String generateToken(UserDetails userDetails) {
    Map<String, Object> claims = new HashMap<>();
    return createToken(claims, userDetails.getUsername());
  }

  /** Creates a JWT token with the provided claims and subject. */
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
   * Retrieves the verification key by converting the secret string to bytes using UTF-8. It also
   * validates that the key is of sufficient length (256 bits for HS256).
   */
  private SecretKey getVerificationKey() {
    byte[] keyBytes = jwtProperties.getSecret().getBytes(StandardCharsets.UTF_8);
    if (keyBytes.length < 32) {
      throw new IllegalArgumentException("The JWT secret key must be at least 32 bytes long.");
    }
    return Keys.hmacShaKeyFor(keyBytes);
  }

  /** Extracts the username (subject) from the JWT token. */
  public String extractUsername(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  /** Extracts the expiration date from the JWT token. */
  public Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  /** Generic method to extract a specific claim using a claims resolver function. */
  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  /** Parses the JWT token and extracts all claims. */
  private Claims extractAllClaims(String token) {
    return Jwts.parser()
        .verifyWith(getVerificationKey())
        .build()
        .parseSignedClaims(token)
        .getPayload();
  }

  /** Checks if the token is expired. */
  private boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  /** Validates the token by checking the username and expiration. */
  public boolean validateToken(String token) {
    String username = extractUsername(token);
    return !isTokenExpired(token);
  }

  /** Retrieves the JWT token value from cookies present in the HTTP request. */
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

  /** Generates an HTTP-only, secure cookie containing the JWT token. */
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

  /** Generates a cleared cookie for logging out. */
  public ResponseCookie getCleanJwtCookie() {
    return ResponseCookie.from(jwtProperties.getCookieName(), "")
        .path("/")
        .maxAge(0)
        .httpOnly(true)
        .secure(jwtProperties.isSecured())
        .sameSite("Strict")
        .build();
  }
}
