package com.dmh.registerusers.service;

import com.dmh.registerusers.entity.UserEntity;
import com.dmh.registerusers.entityredis.UserRedis;
import com.dmh.registerusers.postgresrepository.UserPostgresRepository;
import com.dmh.registerusers.redisrepository.UserRedisRepository;
import com.dmh.registerusers.model.UserDTO;
import com.dmh.registerusers.mapperdto.UserMapper;
import jakarta.persistence.Cacheable;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserPostgresRepository postgresRepository;
    private final UserRedisRepository redisRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;


    public UserService(UserPostgresRepository postgresRepository, UserRedisRepository redisRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.postgresRepository = postgresRepository;
        this.redisRepository = redisRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void saveUsers(List<UserDTO> userDTOs) {

        List<UserEntity> users = new ArrayList<>();
        for (UserDTO userDTO : userDTOs) {
            UserEntity user = new UserEntity();
            user.setNyap(userDTO.getNombreyapellido());
            user.setDni(userDTO.getDni());
            user.setEmail(userDTO.getEmail());
            user.setTelefono(userDTO.getTelefono());
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            user.setCvu(userDTO.getCvu());
            user.setAlias(userDTO.getAlias());
            users.add(user);
        }
        postgresRepository.saveAll(users);
    }

    public UserDTO createUser(UserDTO userDTO) {

        Optional<UserEntity> existingUser = postgresRepository.findByEmail(userDTO.getEmail());
        if (existingUser.isPresent()) {
            throw new IllegalArgumentException("El usuario con el correo electrónico " + userDTO.getEmail() + " ya existe.");
        }

        // Encriptar la contraseña
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        // Convertir a entidad de base de datos
        UserEntity userEntity = userMapper.toEntity(userDTO);
        System.out.println("ID antes de PostgreSQL: " + userEntity.getId());

        // Guardar en PostgreSQL
        UserEntity savedUser = postgresRepository.save(userEntity);

        // Guardar en Redis
        UserRedis userRedis = userMapper.toRedis(userDTO);
        userRedis.setId(String.valueOf(savedUser.getId()));
        System.out.println("ID antes de Redis: " + userRedis.getId());

        if (userRedis.getId() == null) {
            throw new IllegalArgumentException("El ID de UserRedis es null. No se puede guardar en Redis.");
        }

        redisRepository.saveUserRedis(userRedis);

        // Retornar el DTO de usuario creado
        return userMapper.toDTO(savedUser);
    }

    @Cacheable()
    public Optional<UserDTO> getUser(String id) {
            // Si no está en Redis, buscar en PostgreSQL
            Optional<UserEntity> userEntity = postgresRepository.findById(id);
            return userEntity.map(userMapper::toDTO);
        }
}

