package com.dmh.registerusers.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/login")
public class AuthController {

    @GetMapping("/meLogin")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Map<String, Object>> getUserInfo(@AuthenticationPrincipal Jwt jwt) {
        if (jwt == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "No se recibió un token JWT");
        }
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("email", jwt.getClaim("email"));
        return ResponseEntity.ok(userInfo);
    }


    @GetMapping("/admin")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<String> getAdminInfo() {
        return ResponseEntity.ok("Solo los administradores pueden ver esto.");
    }

    @GetMapping("/user")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<String> getUserInfo() {
        return ResponseEntity.ok("Solo los usuarios pueden ver esto.");
    }

    @PreAuthorize("hasRole('ROLE_admin')")
    @GetMapping("/dashboard")
    public String getDashboard() {
        return "Bienvenido al Dashboard del Admin";
    }
}


