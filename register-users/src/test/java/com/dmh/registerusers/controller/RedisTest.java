package com.dmh.registerusers.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class RedisTest {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Test
    void testRedisSetAndGet() {
        // Guardar un valor en Redis
        redisTemplate.opsForValue().set("testKey", "SpringBoot Redis Works!");

        // Obtener el valor de Redis
        String value = redisTemplate.opsForValue().get("testKey");

        // Verificar que el valor guardado y recuperado sean iguales
        assertEquals("SpringBoot Redis Works!", value);
    }
}