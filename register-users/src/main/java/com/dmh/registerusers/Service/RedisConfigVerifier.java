package com.dmh.registerusers.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.data.redis.core.RedisTemplate;

@Component
public class RedisConfigVerifier implements CommandLineRunner {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public void run(String... args) throws Exception {
        if (redisTemplate != null) {
            System.out.println("RedisTemplate is correctly configured.");
        } else {
            System.out.println("RedisTemplate is not configured.");
        }
    }
}

