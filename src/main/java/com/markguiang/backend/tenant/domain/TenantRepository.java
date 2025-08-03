package com.markguiang.backend.tenant.domain;

import com.markguiang.backend.base.model.Repository;

import java.util.Optional;
import java.util.UUID;

public interface TenantRepository extends Repository<Tenant> {
  boolean existsByName(String name);

  Optional<UUID> getIdByName(String name);
}