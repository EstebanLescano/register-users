package com.dmh.registerusers.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/login")
public class AuthController {

    @GetMapping("/meLogin")
    public String getUserInfo(@AuthenticationPrincipal Jwt jwt) {
        if (jwt == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "No se recibió un token JWT");
        }
        String email = jwt.getClaim("email");  // Asegurarse de que el token tiene el claim "email"
        return "Usuario autenticado: " + email;
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
}


