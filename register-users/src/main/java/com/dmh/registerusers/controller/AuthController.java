package com.dmh.registerusers.controller;

import com.dmh.registerusers.entity.UserEntity;
import com.dmh.registerusers.model.LoginDTO;
import com.dmh.registerusers.postgresrepository.UserPostgresRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/login")
public class AuthController {
    
    private final UserPostgresRepository userPostgresRepository;

    public AuthController(UserPostgresRepository userPostgresRepository) {
        this.userPostgresRepository = userPostgresRepository;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<String> authenticate(@RequestBody LoginDTO loginDTO) {
        Optional<UserEntity> userOptional = userPostgresRepository.findByEmail(loginDTO.getEmail());

        if (userOptional.isPresent()) {
            UserEntity user = userOptional.get();
            if (user.getPassword().equals(loginDTO.getPassword())) {
                return ResponseEntity.ok("Usuario autenticado correctamente");
            } else {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "La clave es incorrecta.");
            }
        } else {
            // Si el usuario no existe, crear uno nuevo
            UserEntity newUser = new UserEntity();
            newUser.setEmail(loginDTO.getEmail());
            newUser.setPassword(loginDTO.getPassword());  // En un caso real, deberías encriptar la contraseña
            userPostgresRepository.save(newUser);
            return ResponseEntity.ok("Usuario creado exitosamente.");
        }
    }

    @GetMapping("/meLogin")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Map<String, Object>> getUserInfo(@AuthenticationPrincipal Jwt jwt, UserEntity userEntity) {
        if (jwt == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "No se recibió un token JWT");
        }
        String email = jwt.getClaim("email");  // Obtener el email del token

        Optional<UserEntity> userOptional = userPostgresRepository.findByEmail(email);

        if (userOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("El usuario con correo %s no fue encontrado.", email));
        }

        UserEntity user = userOptional.get();
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("name", jwt.getClaim("name"));
        userInfo.put("email", jwt.getClaim("email"));
        userInfo.put("password", user.getPassword());
        return ResponseEntity.ok(userInfo);
    }
}


