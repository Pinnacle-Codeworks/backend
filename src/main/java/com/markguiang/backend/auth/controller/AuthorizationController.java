package com.markguiang.backend.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthorizationController {

    @PreAuthorize("hasAuthority('permission:write')")
    @PostMapping("/authorization")
    public ResponseEntity<String> grantAuthorization() {
        return ResponseEntity.status(200).body("authorized");
    }
}
