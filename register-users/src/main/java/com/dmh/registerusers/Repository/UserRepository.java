package com.dmh.registerusers.Repository;

import com.dmh.registerusers.UserDTO.User;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepository {
    private final HashOperations<String, String, User> hashOperations;

    public UserRepository(RedisTemplate<String, Object> redisTemplate) {
        this.hashOperations = redisTemplate.opsForHash();
    }

    public void save(User user) {
        hashOperations.put("User", user.getId(), user);
    }

    public Optional<User> findById(String id) {
        return Optional.ofNullable(hashOperations.get("User", id));
    }
}
