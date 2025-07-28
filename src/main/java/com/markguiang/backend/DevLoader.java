package com.markguiang.backend;

import com.markguiang.backend.role.domain.Role;
import com.markguiang.backend.tenant.TenantService;
import com.markguiang.backend.user.domain.UserService;
import jakarta.annotation.PostConstruct;
import java.util.UUID;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("dev")
public class DevLoader {
  private final TenantService tenantService;
  private final UserService userService;

  public DevLoader(TenantService tenantService, UserService userService) {
    this.tenantService = tenantService;

    this.userService = userService;
  }

  @PostConstruct
  private void setup() {

    String name1 = "NOT_INFOR";
    String name2 = "INFOR";

    UUID tenant1Id;
    if (!tenantService.isRegistered(name1)) {
      tenant1Id = tenantService.register(name1);
    } else {
      tenant1Id = tenantService.resolveId(name1);
    }

    UUID tenant2Id;
    if (!tenantService.isRegistered(name2)) {
      tenant2Id = tenantService.register(name2);
    } else {
      tenant2Id = tenantService.resolveId(name2);
    }

    String authId1 = "1";
    String authId2 = "2";

    if (!userService.isRegistered(authId1)) {
      userService.register("mark@gmail.com", authId1, Role.ORGANIZER, tenant1Id);
    }

    if (!userService.isRegistered(authId2)) {
      userService.register("admin@gmail.com", authId2, Role.ORGANIZER, tenant2Id);
    }
  }
}
