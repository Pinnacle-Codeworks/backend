package com.markguiang.backend.tenant.web;

import com.markguiang.backend.tenant.TenantContext;
import com.markguiang.backend.user.User;
import com.markguiang.backend.user.UserContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Optional;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class TenantInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(
            HttpServletRequest request, HttpServletResponse response, Object handler) {
        Optional<User> currentUser = UserContext.getUser();
        if (currentUser.isPresent() && currentUser.get().getTenantId() != null) {
            TenantContext.setTenantId(currentUser.get().getTenantId());
        }
        return true;
    }

    @Override
    public void postHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler,
            ModelAndView modelAndView) {
        TenantContext.clearTenantId();
    }

    @Override
    public void afterCompletion(
            HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
    }
}