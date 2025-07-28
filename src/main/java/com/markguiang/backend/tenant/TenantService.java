package com.markguiang.backend.tenant;

import com.markguiang.backend.tenant.exception.TenantDoesNotExistException;
import java.util.UUID;

public class TenantService {

  private final TenantRepository tenantRepository;

  public TenantService(TenantRepository tenantRepository) {
    this.tenantRepository = tenantRepository;
  }

  public UUID register(String name) {
    if (tenantRepository.existsByName(name)) {
      throw new TenantAlreadyExistsException(name);
    }

    Tenant tenant = Tenant.register(name);
    return tenantRepository.save(tenant);
  }

  public boolean isRegistered(String name) {
    return tenantRepository.existsByName(name);
  }

  public UUID resolveId(String name) {
    return tenantRepository
        .getIdByName(name)
        .orElseThrow(() -> new TenantDoesNotExistException(name));
  }
}
