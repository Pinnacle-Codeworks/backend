package com.markguiang.backend.role.domain;

import com.markguiang.backend.role.domain.Roles.Role;
import com.markguiang.backend.role.domain.adapter.GrantedAuthorityImpl;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;

public class RoleService {

  public List<? extends GrantedAuthority> getGrantedAuthoritiesFromRole(Role role) {
    return GrantedAuthorityImpl.getGrantedAuthoritiesFromRole(role);
  }
}
