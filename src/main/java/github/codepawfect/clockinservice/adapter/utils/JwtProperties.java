package github.codepawfect.clockinservice.adapter.utils;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {
  private String secret;
  private long expiration;
  private String cookieName;
  private boolean secured;

  public String getSecret() {
    return secret;
  }

  public void setSecret(String secret) {
    this.secret = secret;
  }

  public long getExpiration() {
    return expiration;
  }

  public void setExpiration(long expiration) {
    this.expiration = expiration;
  }

  public String getCookieName() {
    return cookieName;
  }

  public void setCookieName(String cookieName) {
    this.cookieName = cookieName;
  }

  public boolean isSecured() {
    return secured;
  }

  public void setSecured(boolean secured) {
    this.secured = secured;
  }
}
