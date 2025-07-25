package com.markguiang.backend.infrastructure.auth.tenant;

import com.markguiang.backend.infrastructure.auth.context.TenantContext;
import com.markguiang.backend.infrastructure.auth.context.UserContext;
import com.markguiang.backend.user.domain.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class TenantInterceptor implements HandlerInterceptor {

  public TenantInterceptor() {}

  @Override
  public boolean preHandle(
      HttpServletRequest request, HttpServletResponse response, Object handler) {

    UserContext.getAuthenticatedUser().map(User::getTenantId).ifPresent(TenantContext::setTenantId);

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
      throws Exception {}
}