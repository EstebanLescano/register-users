package com.dmh.registerusers.service;

import com.dmh.registerusers.entity.UserEntity;
import com.dmh.registerusers.entityRedis.UserRedis;
import com.dmh.registerusers.postgresRepository.UserPostgresRepository;
import com.dmh.registerusers.redisRepository.UserRedisRepository;
import com.dmh.registerusers.userDTO.UserDTO;
import com.dmh.registerusers.mapperDTO.UserMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;

@Validated
@Service
public class UserService {
    private final UserPostgresRepository postgresRepository;
    private final UserRedisRepository redisRepository;
    private final UserMapper userMapper;

    public UserService(UserPostgresRepository postgresRepository, UserRedisRepository redisRepository, UserMapper userMapper) {
        this.postgresRepository = postgresRepository;
        this.redisRepository = redisRepository;
        this.userMapper = userMapper;
    }

    public UserDTO createUser(UserDTO user) {
        // Guardar en PostgreSQL
        UserEntity userEntity = userMapper.toEntity(user);
        postgresRepository.save(userEntity);

        // Guardar en Redis
        UserRedis userRedis = userMapper.toRedis(user);
        redisRepository.save(userRedis);

        return userMapper.toDTO(userEntity);
    }

    public Optional<UserDTO> getUser(String id) {
        // Intentar obtener de Redis primero
        Optional<UserRedis> userRedis = redisRepository.findById(id);
        if (userRedis.isPresent()) {
            return Optional.of(userMapper.toDTO(userRedis.get()));
        }

        // Si no está en Redis, buscar en PostgreSQL
        Optional<UserEntity> userEntity = postgresRepository.findById(id);
        return userEntity.map(userMapper::toDTO);
    }
}
