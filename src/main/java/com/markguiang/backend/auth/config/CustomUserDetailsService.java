package com.markguiang.backend.auth.config;

import com.markguiang.backend.auth.role.Role;
import com.markguiang.backend.user.User;
import com.markguiang.backend.user.UserService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserService userService;

    private final Role participantRole;

    private final Role adminRole;

    public CustomUserDetailsService(UserService userService, @Qualifier("participant") Role participantRole,
            @Qualifier("admin") Role adminRole) {
        this.userService = userService;
        this.participantRole = participantRole;
        this.adminRole = adminRole;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.getUserByUsername(username);
        CustomUserDetails userDetails = new CustomUserDetails(userService.getUserByUsername(username));
        List<GrantedAuthority> authorities = getAuthoritiesFromRoles(user.getRoles());
        userDetails.setAuthorities(authorities);
        return userDetails;
    }

    // TODO find a way to store and retrieve authorities for each role
    private List<GrantedAuthority> getAuthoritiesFromRoles(List<Role> roles) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Role role : roles) {
            if (role.equals(participantRole)) {
                authorities.add(new SimpleGrantedAuthority("permission:read"));
            } else if (role.equals(adminRole)) {
                authorities.add(new SimpleGrantedAuthority("permission:read"));
                authorities.add(new SimpleGrantedAuthority("permission:write"));
            }
        }
        return authorities;
    }
}
