package com.markguiang.backend.auth.role;

import com.markguiang.backend.auth.config.enum_.RoleType;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RoleRepository extends CrudRepository<Role, Long> {
    Optional<Role> findByRoleType(RoleType roleType);
}