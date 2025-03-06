package com.dmh.registerusers.mapperDTO;

import com.dmh.registerusers.entity.UserEntity;
import com.dmh.registerusers.userDTO.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDTO toDTO(UserEntity entity);
    UserEntity toEntity(UserDTO user);

    UserDTO toDTO(com.dmh.registerusers.entityRedis.UserRedis redis);
    com.dmh.registerusers.entityRedis.UserRedis toRedis(UserDTO user);
}
