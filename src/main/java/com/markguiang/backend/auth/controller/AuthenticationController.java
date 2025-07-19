package com.markguiang.backend.auth.controller;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import com.markguiang.backend.auth.config.enum_.RoleType;
import com.markguiang.backend.auth.dto.mapper.UserRequestMapper;
import com.markguiang.backend.auth.dto.mapper.UserResponseMapper;
import com.markguiang.backend.auth.dto.request.OAuthDTO;
import com.markguiang.backend.auth.dto.request.RegisterUserDTO;
import com.markguiang.backend.auth.dto.response.LoginResponseDTO;
import com.markguiang.backend.auth.dto.response.UserResponseDTO;
import com.markguiang.backend.auth.role.Role;
import com.markguiang.backend.auth.role.RoleService;
import com.markguiang.backend.user.User;
import com.markguiang.backend.user.UserContext;
import com.markguiang.backend.user.UserRepository;
import com.markguiang.backend.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.context.DelegatingSecurityContextRepository;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final SecurityContextHolderStrategy securityContextHolderStrategy = SecurityContextHolder
            .getContextHolderStrategy();
    private final SecurityContextLogoutHandler securityContextLogoutHandler;
    private final DelegatingSecurityContextRepository delegatingSecurityContextRepository;
    private final RoleService roleService;
    private final UserRequestMapper userRequestMapper;
    private final UserResponseMapper userResponseMapper;
    private final CsrfTokenRepository csrfTokenRepository;
    private final UserRepository userRepository;



    public AuthenticationController(UserService userService, AuthenticationManager authenticationManager,
            SecurityContextLogoutHandler securityContextLogoutHandler,
            DelegatingSecurityContextRepository delegatingSecurityContextRepository, RoleService roleService,
            UserRequestMapper userRequestMapper, UserResponseMapper userResponseMapper, UserRepository userRepository, CsrfTokenRepository csrfTokenRepository) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.securityContextLogoutHandler = securityContextLogoutHandler;
        this.delegatingSecurityContextRepository = delegatingSecurityContextRepository;
        this.roleService = roleService;
        this.userRequestMapper = userRequestMapper;
        this.userResponseMapper = userResponseMapper;
        this.userRepository = userRepository;
        this.csrfTokenRepository = csrfTokenRepository;
    }

    @Operation(summary = "Login user", description = "Requires Basic Authorization header (Base64 encoded 'username:password')", security = @SecurityRequirement(name = "basicAuth"))
    @GetMapping("/user")
    public LoginResponseDTO loginUser(HttpServletRequest request, HttpServletResponse response,
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
        User userResult = userService.getUserByUsername(username);

        if (csrfToken != null) {
            response.setHeader(csrfToken.getHeaderName(), csrfToken.getToken());
            return this.userResponseMapper.userToLoginResponseDTO(userResult, csrfToken.getToken());
        }
        return this.userResponseMapper.userToLoginResponseDTO(userResult, "");
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

    @Operation(summary = "Register new user", description = """
            Registers a new user account with the role of PARTICIPANT.
            """)
    @PostMapping("/user")
    public UserResponseDTO registerUser(@Valid @RequestBody RegisterUserDTO registerUserDTO) {
        User user = this.userRequestMapper.registerUserDTOtoUser(registerUserDTO);
        Role role = roleService.getOrCreateRole(RoleType.PARTICIPANT);
        user.setRoles(List.of(role));
        User userResult = this.userService.registerUser(user);
        return this.userResponseMapper.userToUserResponseDTO(userResult);
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

    @GetMapping("/csrf")
    public ResponseEntity<CsrfToken> getCsrfToken(HttpServletRequest request, HttpServletResponse response) {
        CsrfToken csrfToken = csrfTokenRepository.loadToken(request);

        if (csrfToken == null) {
            csrfToken = csrfTokenRepository.generateToken(request);
            csrfTokenRepository.saveToken(csrfToken, request, response);
        }

        response.setHeader(csrfToken.getHeaderName(), csrfToken.getToken());
        return ResponseEntity.ok(csrfToken);
    }

    @PostMapping("/oauth")
    public ResponseEntity<Object> verifyToken(
            @RequestBody OAuthDTO request,
            HttpServletRequest httpRequest,
            HttpServletResponse httpResponse
    ) {
        String idToken = request.getToken();
        String emailFromClient = request.getEmail();

        try {
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);
            String emailFromFirebase = decodedToken.getEmail();

            if (!emailFromFirebase.equals(emailFromClient)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Collections.singletonMap("error", "Email mismatch between token and request"));
            }

            boolean isAuthorized = userRepository.existsByEmail(emailFromFirebase);
            if (!isAuthorized) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Collections.singletonMap("error", "Email not authorized"));
            }

            Authentication authentication = new UsernamePasswordAuthenticationToken(emailFromFirebase, null,
                    List.of(new SimpleGrantedAuthority("ROLE_USER")));

            SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
            securityContext.setAuthentication(authentication);
            SecurityContextHolder.setContext(securityContext);

            new HttpSessionSecurityContextRepository()
                    .saveContext(securityContext, httpRequest, httpResponse);

            CsrfToken csrfToken = csrfTokenRepository.loadToken(httpRequest);
            if (csrfToken == null) {
                csrfToken = csrfTokenRepository.generateToken(httpRequest);
                csrfTokenRepository.saveToken(csrfToken, httpRequest, httpResponse);
            }

            httpResponse.setHeader(csrfToken.getHeaderName(), csrfToken.getToken());

            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("status", "authorized");
            responseBody.put("csrf", csrfToken);
            responseBody.put("email", emailFromFirebase);

            return ResponseEntity.ok(responseBody);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Collections.singletonMap("error", "Invalid token"));
        }
    }

}
