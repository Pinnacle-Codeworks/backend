package com.markguiang.backend;

import com.markguiang.backend.role.domain.Roles.Role;
import com.markguiang.backend.tenant.Tenant;
import com.markguiang.backend.tenant.TenantRepository;
import com.markguiang.backend.user.domain.UserService;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("dev")
public class DevLoader {
  private final TenantRepository tenantRepository;
  private final com.markguiang.backend.user.domain.UserService userService;

  public DevLoader(TenantRepository tenantRepository, UserService userService) {
    this.tenantRepository = tenantRepository;
    this.userService = userService;
  }

  @PostConstruct
  private void setup() {
    Tenant tenant1 = new Tenant();
    tenant1.setName("NOT_INFOR");
    tenantRepository.save(tenant1);

    Tenant tenant2 = new Tenant();
    tenant2.setName("INFOR");
    tenantRepository.save(tenant2);

    String authId1 = "1";
    String authId2 = "2";
    if (!userService.isRegistered(authId1)) {
      userService.register("mark@gmail.com", authId1, Role.ORGANIZER, tenant1.getTenantId());
    }
    if (!userService.isRegistered(authId2)) {
      userService.register("admin@gmail.com", authId2, Role.ORGANIZER, tenant2.getTenantId());
    }
  }
}
