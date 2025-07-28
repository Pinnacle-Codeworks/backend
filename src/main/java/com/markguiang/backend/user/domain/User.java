package com.markguiang.backend.user.domain;

import com.markguiang.backend.base.model.AggregateRoot;
import com.markguiang.backend.role.domain.Role;
import java.util.UUID;

public class User extends AggregateRoot {

  public static User register(String email, String authId, Role role, UUID tenantId) {
    return new User(email, authId, role, tenantId);
  }

  public static User loadFromPersistence(
          UUID id, String email, String authId, Role role, UUID tenantId) {
    return new User(id, email, authId, role, tenantId);
  }

  private UUID tenantId;
  private final String authId;
  private String email;
  private Role role;
  private String firstName;
  private String lastName;

  private User(String email, String authId, Role role, UUID tenantId) {
    this(null, email, authId, role, tenantId);
  }

  private User(UUID id, String email, String authId, Role role, UUID tenantId) {
    super(id);
    this.email = email;
    this.authId = authId;
    this.role = role;
    this.tenantId = tenantId;
  }

  public String getEmail() {
    return email;
  }

  public String getAuthId() {
    return authId;
  }

  public Role getRole() {
    return role;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public UUID getTenantId() {
    return tenantId;
  }
}