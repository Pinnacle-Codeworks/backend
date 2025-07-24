package com.markguiang.backend.auth.config;

import com.markguiang.backend.role.domain.RoleService;
import com.markguiang.backend.user.domain.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.context.DelegatingSecurityContextRepository;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;
import org.springframework.security.web.csrf.XorCsrfTokenRequestAttributeHandler;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
  @Bean
  public SecurityFilterChain securityFilterChain(
      HttpSecurity http, @Value("${spring.profiles.active}") String activeProfile)
      throws Exception {
    if ("dev".equals(activeProfile)) {
      http.csrf().disable();
    } else {
      XorCsrfTokenRequestAttributeHandler requestHandler =
          new XorCsrfTokenRequestAttributeHandler();
      requestHandler.setCsrfRequestAttributeName("_csrf");
      http.csrf(
          csrf ->
              csrf.csrfTokenRequestHandler(requestHandler).ignoringRequestMatchers("/auth/user"));
    }

    http.authorizeHttpRequests(
            authorize ->
                authorize
                    .requestMatchers("/auth/**", "/v3/api-docs/**", "/swagger-ui/**")
                    .permitAll()
                    .anyRequest()
                    .authenticated())
        .securityContext(
            (securityContext) ->
                securityContext.securityContextRepository(
                    new DelegatingSecurityContextRepository(
                        new RequestAttributeSecurityContextRepository(),
                        new HttpSessionSecurityContextRepository())));
    return http.build();
  }

  @Bean
  public AuthenticationManager authenticationManager(UserService us, RoleService rs) {
    AuthenticationProvider uAuthenticationProvider = new UserAuthenticationProvider(us, rs);

    ProviderManager providerManager = new ProviderManager(uAuthenticationProvider);
    return providerManager;
  }

  @Bean
  public DelegatingSecurityContextRepository delegatingSecurityContextRepository() {
    return new DelegatingSecurityContextRepository(
        new HttpSessionSecurityContextRepository(),
        new RequestAttributeSecurityContextRepository());
  }

  @Bean
  public SecurityContextLogoutHandler securityContextLogoutHandler() {
    return new SecurityContextLogoutHandler();
  }
}
