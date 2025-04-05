package github.codepawfect.clockinservice.adapter.auth.out.model;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import github.codepawfect.clockinservice.domain.user.model.NewUser;
import github.codepawfect.clockinservice.domain.user.model.User;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/** UserDocument is a model class that represents a user document in the database. */
@Document(collection = "users")
public record UserDocument(@Id String id, String username, String password, List<String> roles)
    implements UserDetails {

  public static UserDocument from(NewUser newUser) {
    return new UserDocument(
        null,
        newUser.username(),
        newUser.password(),
        newUser.roles() != null ? newUser.roles() : List.of("USER"));
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return roles.stream()
        .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
        .collect(Collectors.toList());
  }

  public static User toDomain(UserDocument userDocument) {
    return new User(userDocument.username, userDocument.roles);
  }

  @Override
  public String getPassword() {
    return this.password;
  }

  @Override
  public String getUsername() {
    return this.username;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
