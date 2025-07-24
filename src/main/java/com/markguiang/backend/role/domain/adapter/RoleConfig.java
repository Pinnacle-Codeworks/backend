package com.markguiang.backend.role.domain.adapter;

import com.markguiang.backend.role.domain.RoleService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RoleConfig {
  @Bean
  public RoleService roleService() {
    return new RoleService();
  }
}
