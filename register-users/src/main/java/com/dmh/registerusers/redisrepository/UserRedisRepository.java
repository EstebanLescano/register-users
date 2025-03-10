package com.dmh.registerusers.redisrepository;

import com.dmh.registerusers.entityredis.UserRedis;
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

    public void saveUserRedis(UserRedis userRedis) {
        String key = "User:" + String.valueOf(userRedis.getId());
        hashOperations.put("User", userRedis.getId(), userRedis);
        // ⏳ Establecer tiempo de expiración (ejemplo: 60 minutos)
        redisTemplate.expire(key, Duration.ofMinutes(60));
    }

    public Optional<UserRedis> findById(String id) {
        return Optional.ofNullable(hashOperations.get("User", id));
    }
}
