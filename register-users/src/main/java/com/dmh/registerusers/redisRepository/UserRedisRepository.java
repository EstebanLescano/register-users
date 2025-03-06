package com.dmh.registerusers.redisRepository;

import com.dmh.registerusers.entityRedis.UserRedis;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.util.Optional;

@Repository
public class UserRedisRepository {

    private final HashOperations<String, String, UserRedis> hashOperations;
    private final RedisTemplate<String, Object> redisTemplate;

    public UserRedisRepository(RedisTemplate<String, Object> redisTemplate) {
        this.hashOperations = redisTemplate.opsForHash();
        this.redisTemplate = redisTemplate;
    }

    public UserRedis save(UserRedis userRedis) {
        String key = "User:" + userRedis.getId();
        hashOperations.put("User", userRedis.getId(), userRedis);
        // ⏳ Establecer tiempo de expiración (ejemplo: 60 minutos)
        redisTemplate.expire(key, Duration.ofMinutes(60));
        return userRedis;
    }

    public Optional<UserRedis> findById(String id) {
        return Optional.ofNullable(hashOperations.get("User", id));
    }
}
