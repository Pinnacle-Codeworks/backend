package com.markguiang.backend;

import com.markguiang.backend.auth.config.enum_.RoleType;
import com.markguiang.backend.auth.role.Role;
import com.markguiang.backend.auth.role.RoleService;
import com.markguiang.backend.tenant.Tenant;
import com.markguiang.backend.tenant.TenantRepository;
import com.markguiang.backend.user.User;
import com.markguiang.backend.user.UserRepository;
import com.markguiang.backend.user.UserService;
import jakarta.annotation.PostConstruct;
import java.util.List;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("dev")
public class DevLoader {
    private final TenantRepository tenantRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final RoleService roleService;

    public DevLoader(
            TenantRepository tenantRepository, UserRepository userRepository, UserService userService, RoleService roleService) {
        this.tenantRepository = tenantRepository;
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.userService = userService;
    }

    @PostConstruct
    private void setup() {
        if (tenantRepository.count() == 0) {
            Tenant tenant = new Tenant();
            tenant.setName("NOT_INFOR");
            tenantRepository.save(tenant);

            if (!userRepository.existsByEmail("admin@gmail.com")) {
                User user = new User();
                user.setUsername("admin");
                user.setEmail("admin@gmail.com");
                user.setPassword("admin");
                user.setTenantId(tenant.getTenantId());
                Role role = roleService.getOrCreateRole(RoleType.ADMIN);
                user.setRoles(List.of(role));
                userService.registerUser(user);
            }

            Tenant tenant2 = new Tenant();
            tenant2.setName("INFOR");
            tenantRepository.save(tenant2);

            if (!userRepository.existsByEmail("admin2@gmail.com")) {
                User user2 = new User();
                user2.setUsername("admin2");
                user2.setEmail("admin2@gmail.com");
                user2.setPassword("admin");
                user2.setTenantId(tenant2.getTenantId());
                Role role2 = roleService.getOrCreateRole(RoleType.ADMIN);
                user2.setRoles(List.of(role2));
                userService.registerUser(user2);
            }
        }
    }
}
