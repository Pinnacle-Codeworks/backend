package com.markguiang.backend.auth;

import com.markguiang.backend.user.User;
import com.markguiang.backend.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.web.context.DelegatingSecurityContextRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final SecurityContextHolderStrategy securityContextHolderStrategy = SecurityContextHolder.getContextHolderStrategy();
    private final DelegatingSecurityContextRepository delegatingSecurityContextRepository;

    public AuthController(UserService userService, AuthenticationManager authenticationManager, DelegatingSecurityContextRepository delegatingSecurityContextRepository) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.delegatingSecurityContextRepository = delegatingSecurityContextRepository;
    }

    @GetMapping("/user")
    public ResponseEntity<String> loginUser(@RequestBody User user, HttpServletRequest request , HttpServletResponse response) {
        Authentication authenticationRequest = UsernamePasswordAuthenticationToken.unauthenticated(user.getUsername(), user.getPassword());
        Authentication authenticationResponse = this.authenticationManager.authenticate(authenticationRequest);

        SecurityContext securityContext = securityContextHolderStrategy.createEmptyContext();
        securityContext.setAuthentication(authenticationResponse);
        this.securityContextHolderStrategy.setContext(securityContext);
        this.delegatingSecurityContextRepository.saveContext(securityContext, request, response);
        return ResponseEntity.status(HttpStatus.OK).body("Successfully logged in.");
    }

    @PostMapping("/user")
    public User registerUser(@RequestBody User user) {
        return this.userService.registerUser(user);
    }
}
