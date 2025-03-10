package com.dmh.registerusers.mapperdto;

import com.dmh.registerusers.entity.UserEntity;
import com.dmh.registerusers.entityredis.UserRedis;
import com.dmh.registerusers.model.UserDTO;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-07T13:22:47-0300",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 21.0.6 (Amazon.com Inc.)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserDTO toDTO(UserEntity entity) {
        if ( entity == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        if ( entity.getId() != null ) {
            userDTO.setId( entity.getId().toString() );
        }
        userDTO.setName( entity.getName() );
        userDTO.setEmail( entity.getEmail() );
        userDTO.setPassword( entity.getPassword() );

        return userDTO;
    }

    @Override
    public UserEntity toEntity(UserDTO user) {
        if ( user == null ) {
            return null;
        }

        UserEntity userEntity = new UserEntity();

        if ( user.getId() != null ) {
            userEntity.setId( UUID.fromString( user.getId() ) );
        }
        userEntity.setName( user.getName() );
        userEntity.setEmail( user.getEmail() );
        userEntity.setPassword( user.getPassword() );

        return userEntity;
    }

    @Override
    public UserDTO toDTO(UserRedis redis) {
        if ( redis == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( redis.getId() );
        userDTO.setName( redis.getName() );
        userDTO.setEmail( redis.getEmail() );
        userDTO.setPassword( redis.getPassword() );

        return userDTO;
    }

    @Override
    public UserRedis toRedis(UserDTO user) {
        if ( user == null ) {
            return null;
        }

        UserRedis userRedis = new UserRedis();

        userRedis.setName( user.getName() );
        userRedis.setEmail( user.getEmail() );
        userRedis.setPassword( user.getPassword() );

        userRedis.setId( user.getId() != null ? user.getId().toString() : null );

        return userRedis;
    }
}
