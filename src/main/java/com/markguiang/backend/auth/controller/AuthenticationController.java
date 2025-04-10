package com.markguiang.backend.auth.controller;

import com.markguiang.backend.auth.config.enum_.RoleType;
import com.markguiang.backend.auth.dto.mapper.UserMapper;
import com.markguiang.backend.auth.dto.request.RegisterUserDTO;
import com.markguiang.backend.auth.dto.response.LoginResponseDTO;
import com.markguiang.backend.auth.dto.response.UserResponseDTO;
import com.markguiang.backend.auth.role.Role;
import com.markguiang.backend.auth.role.RoleService;
import com.markguiang.backend.user.User;
import com.markguiang.backend.user.UserContext;
import com.markguiang.backend.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
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
import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final SecurityContextHolderStrategy securityContextHolderStrategy = SecurityContextHolder.getContextHolderStrategy();
    private final SecurityContextLogoutHandler securityContextLogoutHandler;
    private final DelegatingSecurityContextRepository delegatingSecurityContextRepository;
    private final RoleService roleService;
    private final UserContext userContext;
    private final UserMapper userMapper;

    public AuthenticationController(UserService userService, AuthenticationManager authenticationManager, SecurityContextLogoutHandler securityContextLogoutHandler, DelegatingSecurityContextRepository delegatingSecurityContextRepository, RoleService roleService,  UserContext userContext, UserMapper userMapper) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.securityContextLogoutHandler = securityContextLogoutHandler;
        this.delegatingSecurityContextRepository = delegatingSecurityContextRepository;
        this.roleService = roleService;
        this.userContext = userContext;
        this.userMapper = userMapper;
    }

    @GetMapping("/user")
    public LoginResponseDTO loginUser(HttpServletRequest request, HttpServletResponse response, CsrfToken csrfToken) {
        String[] credentials = getCredentialsFromRequest(request);
        String username = credentials[0];
        String password = credentials[1];

        Authentication authenticationRequest = UsernamePasswordAuthenticationToken.unauthenticated(username, password);
        Authentication authenticationResponse = this.authenticationManager.authenticate(authenticationRequest);

        SecurityContext securityContext = securityContextHolderStrategy.createEmptyContext();
        securityContext.setAuthentication(authenticationResponse);
        this.securityContextHolderStrategy.setContext(securityContext);
        this.delegatingSecurityContextRepository.saveContext(securityContext, request, response);

        userContext.initialize();
        User userResult = userService.getUserByUsername(username);

        if (csrfToken != null) {
            response.setHeader(csrfToken.getHeaderName(), csrfToken.getToken());
            return this.userMapper.userToLoginResponseDTO(userResult, csrfToken.getToken());
        }
        return this.userMapper.userToLoginResponseDTO(userResult, "");
    }

    @DeleteMapping("/user")
    public ResponseEntity<String> logoutUser(Authentication authentication, HttpServletRequest request, HttpServletResponse response) {
        this.securityContextLogoutHandler.logout(request, response, authentication);
        return ResponseEntity.status(HttpStatus.OK).body("Successfully logged out.");
    }

    @PostMapping("/user")
    public UserResponseDTO registerUser(@Valid @RequestBody RegisterUserDTO registerUserDTO) {
        User user = this.userMapper.registerUserDTOtoUser(registerUserDTO);
        Role role = roleService.getOrCreateRole(RoleType.PARTICIPANT);
        user.setRoles(List.of(role));
        User userResult = this.userService.registerUser(user);
        return this.userMapper.userToUserResponseDTO(userResult);
    }

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