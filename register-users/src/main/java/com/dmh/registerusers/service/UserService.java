package com.dmh.registerusers.service;

import com.dmh.registerusers.entity.UserEntity;
import com.dmh.registerusers.entityredis.UserRedis;
import com.dmh.registerusers.postgresrepository.UserPostgresRepository;
import com.dmh.registerusers.redisrepository.UserRedisRepository;
import com.dmh.registerusers.model.UserDTO;
import com.dmh.registerusers.mapperdto.UserMapper;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


@Service
public class UserService {
    private final UserPostgresRepository postgresRepository;
    private final UserRedisRepository redisRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final CacheManager cacheManager;

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public UserService(UserPostgresRepository postgresRepository, UserRedisRepository redisRepository, UserMapper userMapper, PasswordEncoder passwordEncoder, CacheManager cacheManager) {
        this.postgresRepository = postgresRepository;
        this.redisRepository = redisRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.cacheManager = cacheManager;
    }

    public void createUser(UserDTO userDTO) {

        postgresRepository.findByEmail(userDTO.getEmail()).ifPresent(user -> {
            throw new IllegalArgumentException(String.format("El usuario con email %s ya existe.", userDTO.getEmail()));
        });

        if (userDTO.getDni().isEmpty()) {
            throw new IllegalArgumentException("El DNI no puede estar vacío.");
        }

        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        UserEntity savedUser = postgresRepository.save(userMapper.toEntity(userDTO));

        UserRedis userRedis = userMapper.toRedis(userDTO);
        userRedis.setId(String.valueOf(savedUser.getId()));
        redisRepository.saveUserRedis(userRedis);

        cacheUser(savedUser);
    }

    public void createUsersBulk(List<UserDTO> userDTOs) {
        List<UserEntity> entities = userDTOs.stream().map(dto -> {
            if (postgresRepository.findByEmail(dto.getEmail()).isPresent() || dto.getDni().isEmpty()) {
                throw new IllegalArgumentException(String.format("El usuario con email %s ya existe.", dto.getEmail()));
            }
            dto.setPassword(passwordEncoder.encode(dto.getPassword()));
            return userMapper.toEntity(dto);
        }).toList();

        List<UserEntity> savedUsers = postgresRepository.saveAll(entities);
        savedUsers.forEach(this::cacheUser);
    }

    @Cacheable(value = "users", key = "#id")
    public UserDTO getUserById(String id) {
        Cache cache = cacheManager.getCache("users");
        if (cache != null && cache.get(id) != null) {
            logger.info("Usuario con ID {} obtenido desde Redis cache.", id);
        } else {
            logger.info("Usuario con ID {} no encontrado en caché, consultando en PostgreSQL.", id);
        }

        UserEntity userEntity = postgresRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(String.format("Usuario con ID %s no encontrado.", id)));

        return userMapper.toDTO(userEntity);
    }

    public List<UserEntity> getAllUsers() {
        return postgresRepository.findAll();

    }
    @CacheEvict(value = "users", key = "#id")
    public void deleteUser(String id) {
        if (!postgresRepository.existsById(id)) {
            throw new IllegalArgumentException(String.format("Usuario con ID %s no encontrado.", id));
        }
        postgresRepository.deleteById(id);
    }

    private void cacheUser(UserEntity user) {
        Cache cache = cacheManager.getCache("users");
        if (cache != null) {
            cache.put(user.getId(), userMapper.toDTO(user));
            logger.info("Usuario con ID {} almacenado en caché.", user.getId());
        }
    }

}

