package com.dmh.registerusers.mapperdto;

import com.dmh.registerusers.entity.UserEntity;
import com.dmh.registerusers.entityredis.UserRedis;
import com.dmh.registerusers.model.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDTO toDTO(UserEntity entity);

    UserEntity toEntity(UserDTO user);

    UserDTO toDTO(UserRedis redis);

    UserRedis toRedis(UserDTO user);

}
