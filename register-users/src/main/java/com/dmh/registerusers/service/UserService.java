package com.dmh.registerusers.service;

import com.dmh.registerusers.entity.UserEntity;
import com.dmh.registerusers.entityredis.UserRedis;
import com.dmh.registerusers.postgresrepository.UserPostgresRepository;
import com.dmh.registerusers.redisrepository.UserRedisRepository;
import com.dmh.registerusers.model.UserDTO;
import com.dmh.registerusers.mapperdto.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;


@Service
public class UserService {
    private final UserPostgresRepository postgresRepository;
    private final UserRedisRepository redisRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private CacheManager cacheManager;

    public UserService(UserPostgresRepository postgresRepository, UserRedisRepository redisRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.postgresRepository = postgresRepository;
        this.redisRepository = redisRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public void createUser(UserDTO userDTO) {

        Optional<UserEntity> existingUser = postgresRepository.findByEmail(userDTO.getEmail());
        if (existingUser.isPresent()|| userDTO.getDni().isEmpty()) {
            throw new IllegalArgumentException("El usuario con el correo electrónico " + userDTO.getEmail() + " ya existe.");
        }
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        // Convertir a entidad de base de datos
        UserEntity userEntity = userMapper.toEntity(userDTO);
        System.out.println("ID antes de PostgreSQL: " + userEntity.getId());

        // Guardar en PostgreSQL
        UserEntity savedUser = postgresRepository.save(userEntity);

        // Guardar en Redis
        UserRedis userRedis = userMapper.toRedis(userDTO);
        userRedis.setId(String.valueOf(savedUser.getId()));

        if (userRedis.getId() == null) {
            throw new IllegalArgumentException("El ID de UserRedis es null. No se puede guardar en Redis.");
        }

        redisRepository.saveUserRedis(userRedis);

        Cache cache = cacheManager.getCache("users");
        if (cache != null) {
            cache.put(savedUser.getId(), userMapper.toDTO(savedUser));  // Guardar en caché con ID como clave
        }

        // Retornar el DTO de usuario creado
        userMapper.toDTO(savedUser);
    }

    public void createUsersBulk(List<UserDTO> userDTOs) {
        for (UserDTO userDTO : userDTOs) {
            Optional<UserEntity> existingUser = postgresRepository.findByEmail(userDTO.getEmail());

            // Verificar si el correo electrónico ya está registrado o si el DNI está vacío
            if (existingUser.isPresent() || userDTO.getDni().isEmpty()) {
                throw new IllegalArgumentException("El usuario con el correo electrónico " + userDTO.getEmail() + " ya existe o el DNI está vacío.");
            }

            // Codificar la contraseña
            userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));

            // Convertir a entidad de base de datos
            UserEntity userEntity = userMapper.toEntity(userDTO);

            // Guardar en PostgreSQL
            UserEntity savedUser = postgresRepository.save(userEntity);

            // Guardar en Redis
            UserRedis userRedis = userMapper.toRedis(userDTO);
            userRedis.setId(String.valueOf(savedUser.getId()));

            // Verificar si el ID de Redis es nulo
            if (userRedis.getId() == null) {
                throw new IllegalArgumentException("El ID de UserRedis es null. No se puede guardar en Redis.");
            }

            // Guardar en Redis
            redisRepository.saveUserRedis(userRedis);
        }

        // Retornar el DTO de los usuarios creados (opcional, si lo necesitas)
        userDTOs.stream()
                .map(userMapper::toDTO)
                .toList();

    }

    @Cacheable(value = "users", key = "#id")  // La clave es el ID del usuario
    public UserDTO getUserById(String id) {
        Cache cache = cacheManager.getCache("users");
        if (cache != null && cache.get(id) != null) {
            logger.info("El usuario con ID: %d fue encontrado en Redis cache.");
        } else {
            logger.info("Consultando usuario con ID: %d and %d desde PostgreSQL.");
        }
        UserEntity userEntity = postgresRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
        logger.info("El usuario con ID %d no estaba en la caché, se ha consultado desde PostgreSQL.");
        return userMapper.toDTO(userEntity);
    }

    public List<UserEntity> getAllUsers() {
        return postgresRepository.findAll();

    }
    @CacheEvict(value = "users", key = "#id")
    public void deleteUser(String id) {
        if (!postgresRepository.existsById(id)) {
            throw new IllegalArgumentException("Usuario con ID %d no encontrado.");
        }
        postgresRepository.deleteById(id);
    }


}

