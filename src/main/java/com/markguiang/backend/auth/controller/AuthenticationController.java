package com.markguiang.backend.auth.controller;

import com.markguiang.backend.infrastructure.auth.UserContext;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.context.DelegatingSecurityContextRepository;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@RestController
@RequestMapping("/auth")
@Profile("dev")
public class AuthenticationController {
  private final AuthenticationManager authenticationManager;
  private final SecurityContextHolderStrategy securityContextHolderStrategy = SecurityContextHolder
      .getContextHolderStrategy();
  private final SecurityContextLogoutHandler securityContextLogoutHandler;
  private final DelegatingSecurityContextRepository delegatingSecurityContextRepository;

  public AuthenticationController(AuthenticationManager authenticationManager,
      SecurityContextLogoutHandler securityContextLogoutHandler,
      DelegatingSecurityContextRepository delegatingSecurityContextRepository) {
    this.authenticationManager = authenticationManager;
    this.securityContextLogoutHandler = securityContextLogoutHandler;
    this.delegatingSecurityContextRepository = delegatingSecurityContextRepository;
  }

  @Operation(summary = "Login user", description = "Requires Basic Authorization header (Base64 encoded 'username:password')", security = @SecurityRequirement(name = "basicAuth"))
  @GetMapping("/user")
  public void loginUser(HttpServletRequest request, HttpServletResponse response,
      @Parameter(hidden = true) CsrfToken csrfToken) {
    String[] credentials = getCredentialsFromRequest(request);
    String username = credentials[0];
    String password = credentials[1];

    Authentication authenticationRequest = UsernamePasswordAuthenticationToken.unauthenticated(username, password);
    Authentication authenticationResponse = this.authenticationManager.authenticate(authenticationRequest);

    SecurityContext securityContext = securityContextHolderStrategy.createEmptyContext();
    securityContext.setAuthentication(authenticationResponse);
    this.securityContextHolderStrategy.setContext(securityContext);
    this.delegatingSecurityContextRepository.saveContext(securityContext, request, response);

    UserContext.setUserContext();

    if (csrfToken != null) {
      response.setHeader(csrfToken.getHeaderName(), csrfToken.getToken());
    }
  }

  @Operation(summary = "Logout user", description = """
         Logs out the currently authenticated user by invalidating their session and clearing the security context.
      """)
  @DeleteMapping("/user")
  public ResponseEntity<String> logoutUser(Authentication authentication, HttpServletRequest request,
      HttpServletResponse response) {
    this.securityContextLogoutHandler.logout(request, response, authentication);
    return ResponseEntity.status(HttpStatus.OK).body("Successfully logged out.");
  }

  // @Operation(summary = "Register new user", description = """
  // Registers a new user account with the role of PARTICIPANT.
  // """)
  // @PostMapping("/user")
  // public UserResponseDTO registerUser(@Valid @RequestBody RegisterUserDTO
  // registerUserDTO) {
  // User user = this.userRequestMapper.registerUserDTOtoUser(registerUserDTO);
  // Role role = roleService.getOrCreateRole(RoleType.PARTICIPANT);
  // user.setRoles(List.of(role));
  // User userResult = this.userService.registerUser(user);
  // return this.userResponseMapper.userToUserResponseDTO(userResult);
  // }
  //

  private String[] getCredentialsFromRequest(HttpServletRequest request) {
    String authHeader = request.getHeader("Authorization");
    if (authHeader == null || !authHeader.startsWith("Basic ")) {
      throw new AuthenticationCredentialsNotFoundException("Missing or invalid Authorization header");
    }

    String base64Credentials = authHeader.substring("Basic ".length());
    byte[] decodedBytes = Base64.getDecoder().decode(base64Credentials);
    String credentials = new String(decodedBytes, StandardCharsets.UTF_8);

    String[] values = credentials.split(":", 2);
    if (values.length != 2) {
      throw new AuthenticationCredentialsNotFoundException("Missing or invalid Authorization header");
    }

    return values;
  }
}
