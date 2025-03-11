package com.dmh.registerusers.service;

import com.dmh.registerusers.entity.UserEntity;
import com.dmh.registerusers.model.LoginDTO;
import com.dmh.registerusers.postgresrepository.UserPostgresRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserPostgresRepository userPostgresRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public AuthService(UserPostgresRepository userPostgresRepository, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider) {
        this.userPostgresRepository = userPostgresRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public String authenticateUser(LoginDTO loginDTO) {
        // Verificar si el usuario existe
        UserEntity user = userPostgresRepository.findByEmail(loginDTO.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        // Verificar si la contraseña es correcta
        if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Credenciales inválidas");
        }

        // Generar el token JWT
        return jwtTokenProvider.createToken(user);
    }
}

