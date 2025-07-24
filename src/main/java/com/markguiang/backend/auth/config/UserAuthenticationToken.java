package com.markguiang.backend.auth.config;

import com.markguiang.backend.user.domain.User;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class UserAuthenticationToken extends AbstractAuthenticationToken {
  public static UserAuthenticationToken unauthenticated(String authId) {
    return new UserAuthenticationToken(authId);
  }

  public static UserAuthenticationToken authenticated(
      User principal, String authId, Collection<? extends GrantedAuthority> authorities) {
    return new UserAuthenticationToken(principal, authId, authorities);
  }

  private final User principal;

  private final String authId;

  private UserAuthenticationToken(
      User principal, String authId, Collection<? extends GrantedAuthority> authorities) {
    super(authorities);
    this.principal = principal;
    this.authId = authId;
    this.setAuthenticated(true);
  }

  private UserAuthenticationToken(String authId) {
    super(new ArrayList<>());
    this.authId = authId;
    this.principal = null;
    this.setAuthenticated(false);
  }

  public User getPrincipal() {
    return principal;
  }

  public UUID getCredentials() {
    return this.getCredentials();
  }

  public String getAuthId() {
    return authId;
  }
}
