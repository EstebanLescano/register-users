package com.dmh.registerusers.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/redis-test")
public class RedisTestController {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @GetMapping("/set")
    public String setKey() {
        redisTemplate.opsForValue().set("testKey", "SpringBoot Redis Works!");
        return "Key set!";
    }

    @GetMapping("/get")
    public String getKey() {
        return redisTemplate.opsForValue().get("testKey OK");
    }
}

