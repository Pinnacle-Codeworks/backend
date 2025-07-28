package com.markguiang.backend.hello;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class HelloController {

  @GetMapping("/")
  public String hello() {
    return "Hello";
  }

  @PreAuthorize("hasAuthority(T(com.markguiang.backend.role.domain.Role.Authority).READ.name())")
  @GetMapping("/read")
  public ResponseEntity<String> grantAuthorization() {
    return ResponseEntity.status(200).body("authorized");
  }

  @PostMapping("/csrfTest")
  public ResponseEntity<String> csrfTest() {
    return ResponseEntity.status(200).body("CSRF GOODS");
  }
}
