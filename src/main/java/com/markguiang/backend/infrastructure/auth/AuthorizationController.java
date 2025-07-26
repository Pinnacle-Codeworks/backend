package com.markguiang.backend.infrastructure.auth;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import com.markguiang.backend.infrastructure.auth.config.UserAuthenticationToken;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthorizationController {
  private final AuthenticationManager authenticationManager;
  private final CsrfTokenRepository csrfTokenRepository;
  private final SecurityContextHolderStrategy securityContextHolderStrategy =
      SecurityContextHolder.getContextHolderStrategy();
  private final SecurityContextRepository securityContextRepository;

  public AuthorizationController(
      AuthenticationManager authenticationManager,
      CsrfTokenRepository csrfTokenRepository,
      SecurityContextRepository securityContextRepository) {
    this.authenticationManager = authenticationManager;
    this.csrfTokenRepository = csrfTokenRepository;
    this.securityContextRepository = securityContextRepository;
  }

  @GetMapping("/login")
  public ResponseEntity<Object> verifyToken(
      @RequestHeader("Authorization") String authorizationHeader,
      HttpServletRequest httpRequest,
      HttpServletResponse httpResponse) {

    try {

      if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(Collections.singletonMap("error", "Missing or invalid Authorization header"));
      }

      String idToken = authorizationHeader.substring(7);
      FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);

      Authentication unauthenticated =
          UserAuthenticationToken.unauthenticated(decodedToken.getUid());
      Authentication authenticated = authenticationManager.authenticate(unauthenticated);

      SecurityContext securityContext = securityContextHolderStrategy.createEmptyContext();
      securityContext.setAuthentication(authenticated);
      securityContextHolderStrategy.setContext(securityContext);
      securityContextRepository.saveContext(securityContext, httpRequest, httpResponse);

      CsrfToken csrfToken = csrfTokenRepository.generateToken(httpRequest);
      csrfTokenRepository.saveToken(csrfToken, httpRequest, httpResponse);

      httpResponse.setHeader(csrfToken.getHeaderName(), csrfToken.getToken());

      Map<String, Object> responseBody = new HashMap<>();
      responseBody.put("csrf", csrfToken);

      return ResponseEntity.ok(responseBody);

    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
          .body(Collections.singletonMap("error", "Invalid token"));
    }
  }
}
