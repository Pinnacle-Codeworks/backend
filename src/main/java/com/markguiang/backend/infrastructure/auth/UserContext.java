package com.markguiang.backend.infrastructure.auth;

import com.markguiang.backend.user.domain.User;
import java.util.Optional;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@SessionScope
@Component
public class UserContext {

  private static User user;

  public static void setUserContext() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    // unsafe
    user = (User) authentication.getPrincipal();
  }

  public static Optional<User> getUser() {
    return Optional.ofNullable(user);
  }
}
