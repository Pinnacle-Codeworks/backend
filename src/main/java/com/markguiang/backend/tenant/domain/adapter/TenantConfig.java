package com.markguiang.backend.tenant.domain.adapter;

import com.markguiang.backend.tenant.domain.TenantRepository;
import com.markguiang.backend.tenant.domain.TenantService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TenantConfig {
  @Bean
  public TenantService tenantService(TenantRepository tenantRepository) {
    return new TenantService(tenantRepository);
  }
}