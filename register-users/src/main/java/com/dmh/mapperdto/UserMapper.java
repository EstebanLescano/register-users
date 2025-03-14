package com.dmh.mapperdto;

import com.dmh.entity.UserEntity;
import com.dmh.entityredis.UserRedis;
import com.dmh.model.UserDTO;
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
