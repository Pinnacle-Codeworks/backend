package com.markguiang.backend.role.domain.adapter;

import com.markguiang.backend.role.domain.Role;
import com.markguiang.backend.role.domain.Role.Authority;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;

public class GrantedAuthorityImpl implements GrantedAuthority {
  public static List<? extends GrantedAuthority> getGrantedAuthoritiesFromRole(Role role) {
    List<Authority> authorities = role.getAuthorities();
    return fromAuthorities(authorities);
  }

  private static List<? extends GrantedAuthority> fromAuthorities(List<Authority> authorities) {
    return authorities.stream().map(GrantedAuthorityImpl::new).toList();
  }

  private final Authority authority;

  public GrantedAuthorityImpl(Authority authority) {
    this.authority = authority;
  }

  @Override
  public String getAuthority() {
    return authority.name();
  }
}
