package com.markguiang.backend.tenant;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TenantRepository extends CrudRepository<Tenant, Long> {
    Optional<Tenant> findByName(String name);
}
