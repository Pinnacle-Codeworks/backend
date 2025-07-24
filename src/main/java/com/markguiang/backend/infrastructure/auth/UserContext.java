package com.markguiang.backend.infrastructure.auth;

import com.markguiang.backend.user.domain.User;
import java.util.Optional;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class UserContext {

  public Optional<User> getAuthenticatedUser() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (auth != null && auth.getPrincipal() instanceof User user) {
      return Optional.of(user);
    }
    return Optional.empty();
  }
}
