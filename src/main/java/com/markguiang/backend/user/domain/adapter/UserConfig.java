package com.markguiang.backend.user.domain.adapter;

import com.markguiang.backend.user.domain.UserRepository;
import com.markguiang.backend.user.domain.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserConfig {
  @Bean
  public UserService userService(UserRepository userRepository) {
    return new UserService(userRepository);
  }
}
