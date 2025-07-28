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

  private final TenantContext tenantContext;
  private final UserContext userContext;

  public TenantInterceptor(TenantContext tenantContext, UserContext userContext) {
    this.tenantContext = tenantContext;
    this.userContext = userContext;
  }

  @Override
  public boolean preHandle(
      HttpServletRequest request, HttpServletResponse response, Object handler) {

    userContext.getAuthenticatedUser().map(User::getTenantId).ifPresent(tenantContext::setTenantId);

    return true;
  }

  @Override
  public void postHandle(
      HttpServletRequest request,
      HttpServletResponse response,
      Object handler,
      ModelAndView modelAndView) {
  }

  @Override
  public void afterCompletion(
      HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
  }
}
