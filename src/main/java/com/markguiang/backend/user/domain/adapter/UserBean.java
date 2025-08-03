package com.markguiang.backend.user.domain.adapter;

import com.markguiang.backend.role.domain.Role;
import com.markguiang.backend.user.domain.User;
import java.util.UUID;

public class UserBean {
  private UUID tenant_id;
  private UUID id;
  private String auth_id;
  private String email;
  private Role role;
  private String first_name;
  private String last_name;

  public User toUser() {
    return User.loadFromPersistence(id, email, auth_id, role, tenant_id);
  }

  public UUID getTenant_id() {
    return tenant_id;
  }

  public void setTenant_id(UUID tenant_id) {
    this.tenant_id = tenant_id;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getAuth_id() {
    return auth_id;
  }

  public void setAuth_id(String auth_id) {
    this.auth_id = auth_id;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Role getRole() {
    return role;
  }

  public void setRole(Role role) {
    this.role = role;
  }

  public String getFirst_name() {
    return first_name;
  }

  public void setFirst_name(String first_name) {
    this.first_name = first_name;
  }

  public String getLast_name() {
    return last_name;
  }

  public void setLast_name(String last_name) {
    this.last_name = last_name;
  }
}