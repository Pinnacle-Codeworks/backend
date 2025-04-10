package com.markguiang.backend.auth.config;

import com.markguiang.backend.auth.config.enum_.RoleType;
import com.markguiang.backend.auth.role.Role;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RoleConfig {

    @Bean
    public Role participantRole() {
        Role role = new Role();
        role.setRoleType(RoleType.PARTICIPANT);
        return role;
    }

    @Bean
    public Role adminRole() {
        Role role = new Role();
        role.setRoleType(RoleType.ADMIN);
        return role;
    }
}