package com.dmh.redisrepository;

import com.dmh.entityredis.UserRedis;

import java.util.Optional;

public interface IUserRedisRepository {

    void saveUserRedis(UserRedis userRedis);
    Optional<UserRedis> findById(String id);


}
