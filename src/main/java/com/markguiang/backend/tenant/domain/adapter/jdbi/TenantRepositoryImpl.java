package com.markguiang.backend.tenant.domain.adapter.jdbi;

import com.markguiang.backend.tenant.Tenant;
import com.markguiang.backend.tenant.TenantRepository;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
public class TenantRepositoryImpl implements TenantRepository {

  private final TenantDao tenantDao;

  public TenantRepositoryImpl(TenantDao tenantDao) {
    this.tenantDao = tenantDao;
  }

  @Override
  public boolean existsByName(String name) {
    return tenantDao.existsByName(name);
  }

  @Override
  public Optional<Tenant> findByID(UUID id) {
    return tenantDao.findByID(id);
  }

  @Override
  public UUID save(Tenant tenant) {
    tenantDao.insertTenant(tenant);
    return tenant.getId();
  }

  @Override
  public Optional<UUID> getIdByName(String name) {
    return tenantDao.getIdByName(name);
  }
}
