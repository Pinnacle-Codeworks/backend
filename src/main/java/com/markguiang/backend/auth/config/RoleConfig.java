package com.markguiang.backend.auth.config;

import com.markguiang.backend.auth.role.Role;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RoleConfig {

    @Bean
    public Role userRole() {
        Role role = new Role();
        role.setName("user");
        return role;
    }

    @Bean
    public Role adminRole() {
        Role role = new Role();
        role.setName("admin");
        return role;
    }
}
