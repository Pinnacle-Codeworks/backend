package com.markguiang.backend.user.domain;

import com.markguiang.backend.role.domain.Roles.Role;
import com.markguiang.backend.user.exception.UserNotFound;
import java.util.Optional;

public class UserService {
  private final UserRepository ur;

  public UserService(UserRepository ur) {
    this.ur = ur;
  }

  public User login(String authId) {
    Optional<User> user = ur.findByAuthId(authId);
    if (user.isEmpty()) {
      throw new UserNotFound();
    }
    return user.get();
  }

  public User register(String email, String authId, Role role, Long tenantId) {
    User user = User.register(email, authId, role, tenantId);
    ur.save(user);
    return user;
  }

  public boolean isRegistered(String authId) {
    return ur.existsByAuthId(authId);
  }
}
