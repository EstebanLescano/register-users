package com.dmh.registerusers.mapperDTO;

import com.dmh.registerusers.entity.UserEntity;
import com.dmh.registerusers.entityRedis.UserRedis;
import com.dmh.registerusers.userDTO.UserDTO;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-06T16:00:49-0300",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 21.0.6 (Amazon.com Inc.)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserDTO toDTO(UserEntity entity) {
        if ( entity == null ) {
            return null;
        }

        String id = null;
        String name = null;
        String email = null;

        id = entity.getId();
        name = entity.getName();
        email = entity.getEmail();

        UserDTO userDTO = new UserDTO( id, name, email );

        return userDTO;
    }

    @Override
    public UserEntity toEntity(UserDTO user) {
        if ( user == null ) {
            return null;
        }

        UserEntity userEntity = new UserEntity();

        return userEntity;
    }

    @Override
    public UserDTO toDTO(UserRedis redis) {
        if ( redis == null ) {
            return null;
        }

        String id = null;
        String name = null;
        String email = null;

        id = redis.getId();
        name = redis.getName();
        email = redis.getEmail();

        UserDTO userDTO = new UserDTO( id, name, email );

        return userDTO;
    }

    @Override
    public UserRedis toRedis(UserDTO user) {
        if ( user == null ) {
            return null;
        }

        UserRedis userRedis = new UserRedis();

        return userRedis;
    }
}
