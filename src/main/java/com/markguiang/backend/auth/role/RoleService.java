package com.markguiang.backend.auth.role;

import org.springframework.stereotype.Service;

@Service
public class RoleService {
    private final RoleRepository roleRepository;
    private final Role userRole;

    public RoleService(RoleRepository roleRepository, Role userRole) {
        this.roleRepository = roleRepository;
        this.userRole = userRole;
    }

    public Role getOrCreateUserRole(String name) {
        return roleRepository.findByName(name).orElseGet(() -> roleRepository.save(userRole));
    }
}
