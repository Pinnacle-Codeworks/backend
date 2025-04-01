package com.markguiang.backend.auth.role;

import com.markguiang.backend.auth.config.enum_.RoleType;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    private final RoleRepository roleRepository;
    private final Role participantRole;
    private final Role adminRole;

    public RoleService(RoleRepository roleRepository, Role participantRole, Role adminRole) {
        this.roleRepository = roleRepository;
        this.participantRole = participantRole;
        this.adminRole = adminRole;
    }

    public Role getOrCreateRole(RoleType roleType) {
        return switch (roleType) {
            case ADMIN ->
                    roleRepository.findByRoleType(roleType).orElseGet(() -> roleRepository.save(adminRole));
            case PARTICIPANT ->
                    roleRepository.findByRoleType(roleType).orElseGet(() -> roleRepository.save(participantRole));
        };
    }
}