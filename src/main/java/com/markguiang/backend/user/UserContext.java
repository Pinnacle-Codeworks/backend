package com.markguiang.backend.user;

import com.markguiang.backend.auth.config.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class UserContext {
    private User user;

    public void initialize() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        this.user = ((CustomUserDetails) authentication.getPrincipal()).getUser();
    }

    public User getUser() {
        return user;
    }
}