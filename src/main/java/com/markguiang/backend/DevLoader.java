package com.markguiang.backend;

import com.markguiang.backend.auth.config.enum_.RoleType;
import com.markguiang.backend.auth.role.Role;
import com.markguiang.backend.auth.role.RoleService;
import com.markguiang.backend.company.Company;
import com.markguiang.backend.company.CompanyRepository;
import com.markguiang.backend.event.model.Event;
import com.markguiang.backend.event.service.EventService;
import com.markguiang.backend.user.User;
import com.markguiang.backend.user.UserRepository;
import com.markguiang.backend.user.UserService;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Profile("dev")
public class DevLoader {
    private final CompanyRepository companyRepository;
    private final UserService  userService;
    private final RoleService roleService;
    private final EventService eventService;

    public DevLoader(CompanyRepository companyRepository, UserService userService, RoleService roleService, EventService eventService) {
        this.companyRepository = companyRepository;
        this.userService = userService;
        this.roleService = roleService;
        this.eventService = eventService;
    }
    @PostConstruct
    private void setup() {
        Company company = companyRepository.save(new Company("NOT_INFOR"));
        User user = new User();
        user.setUsername("admin");
        user.setEmail("admin@gmail.com");
        user.setPassword("admin");
        user.setCompanyId(company.getCompanyId());
        Role role = roleService.getOrCreateRole(RoleType.ADMIN);
        user.setRoles(List.of(role));
        userService.registerUser(user);
    }
}
