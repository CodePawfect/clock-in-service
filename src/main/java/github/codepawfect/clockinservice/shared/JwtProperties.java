package github.codepawfect.clockinservice.shared;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * JwtProperties is a configuration class that holds properties related to JWT (JSON Web Token)
 * authentication.
 */
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {
  private String secret;
  private long expiration;
  private String cookieName;
  private boolean secured;

  public String getSecret() {
    return secret;
  }

  /**
   * Sets the secret key used for signing the JWT.
   *
   * @param secret the secret key
   */
  public void setSecret(String secret) {
    this.secret = secret;
  }

  /**
   * Gets the expiration time of the JWT in milliseconds.
   *
   * @return the expiration time
   */
  public long getExpiration() {
    return expiration;
  }

  /**
   * Sets the expiration time of the JWT in milliseconds.
   *
   * @param expiration the expiration time
   */
  public void setExpiration(long expiration) {
    this.expiration = expiration;
  }

  /**
   * Gets the name of the cookie used to store the JWT.
   *
   * @return the cookie name
   */
  public String getCookieName() {
    return cookieName;
  }

  /**
   * Sets the name of the cookie used to store the JWT.
   *
   * @param cookieName the cookie name
   */
  public void setCookieName(String cookieName) {
    this.cookieName = cookieName;
  }

  /**
   * Checks if the cookie is secured (only sent over HTTPS).
   *
   * @return true if the cookie is secured, false otherwise
   */
  public boolean isSecured() {
    return secured;
  }

  /**
   * Sets whether the cookie is secured (only sent over HTTPS).
   *
   * @param secured true if the cookie is secured, false otherwise
   */
  public void setSecured(boolean secured) {
    this.secured = secured;
  }
}
