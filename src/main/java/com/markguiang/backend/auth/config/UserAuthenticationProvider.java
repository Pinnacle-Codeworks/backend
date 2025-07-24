package com.markguiang.backend.auth.config;

import com.markguiang.backend.role.domain.RoleService;
import com.markguiang.backend.user.domain.User;
import com.markguiang.backend.user.domain.UserService;
import java.util.List;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

public class UserAuthenticationProvider implements AuthenticationProvider {
  private final UserService us;
  private final RoleService rs;

  public UserAuthenticationProvider(UserService us, RoleService rs) {
    this.us = us;
    this.rs = rs;
  }

  @Override
  public Authentication authenticate(Authentication authentication) {
    if (authentication instanceof UserAuthenticationToken uat) {
      User user = us.login(uat.getAuthId());
      List<? extends GrantedAuthority> authorities =
          rs.getGrantedAuthoritiesFromRole(user.getRole());
      return UserAuthenticationToken.authenticated(user, uat.getAuthId(), authorities);
    }
    return null;
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return authentication.equals(UserAuthenticationToken.class);
  }
}
