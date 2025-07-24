package com.markguiang.backend.tenant.web;

import com.markguiang.backend.infrastructure.auth.UserContext;
import com.markguiang.backend.tenant.TenantContext;
import com.markguiang.backend.user.domain.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class TenantInterceptor implements HandlerInterceptor {

  private final UserContext userContext;

  public TenantInterceptor(UserContext userContext) {
    this.userContext = userContext;
  }

  @Override
  public boolean preHandle(
      HttpServletRequest request, HttpServletResponse response, Object handler) {

    userContext.getAuthenticatedUser()
        .map(User::getTenantId)
        .ifPresent(TenantContext::setTenantId);

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
