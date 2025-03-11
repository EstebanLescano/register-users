package com.dmh.registerusers.mapperdto;

import com.dmh.registerusers.entity.UserEntity;
import com.dmh.registerusers.entityredis.UserRedis;
import com.dmh.registerusers.model.UserDTO;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-11T17:27:32-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.6 (Amazon.com Inc.)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserDTO toDTO(UserEntity entity) {
        if ( entity == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setNombreyapellido( entity.getNombreyapellido() );
        userDTO.setDni( entity.getDni() );
        userDTO.setEmail( entity.getEmail() );
        userDTO.setTelefono( entity.getTelefono() );
        userDTO.setPassword( entity.getPassword() );
        userDTO.setCvu( entity.getCvu() );
        userDTO.setAlias( entity.getAlias() );

        return userDTO;
    }

    @Override
    public UserEntity toEntity(UserDTO user) {
        if ( user == null ) {
            return null;
        }

        UserEntity userEntity = new UserEntity();

        userEntity.setNombreyapellido( user.getNombreyapellido() );
        userEntity.setDni( user.getDni() );
        userEntity.setEmail( user.getEmail() );
        userEntity.setTelefono( user.getTelefono() );
        userEntity.setPassword( user.getPassword() );
        userEntity.setCvu( user.getCvu() );
        userEntity.setAlias( user.getAlias() );

        return userEntity;
    }

    @Override
    public UserDTO toDTO(UserRedis redis) {
        if ( redis == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setNombreyapellido( redis.getNombreyapellido() );
        userDTO.setDni( redis.getDni() );
        userDTO.setEmail( redis.getEmail() );
        userDTO.setTelefono( redis.getTelefono() );
        userDTO.setPassword( redis.getPassword() );
        userDTO.setCvu( redis.getCvu() );
        userDTO.setAlias( redis.getAlias() );

        return userDTO;
    }

    @Override
    public UserRedis toRedis(UserDTO user) {
        if ( user == null ) {
            return null;
        }

        UserRedis userRedis = new UserRedis();

        userRedis.setNombreyapellido( user.getNombreyapellido() );
        userRedis.setDni( user.getDni() );
        userRedis.setEmail( user.getEmail() );
        userRedis.setTelefono( user.getTelefono() );
        userRedis.setPassword( user.getPassword() );
        userRedis.setCvu( user.getCvu() );
        userRedis.setAlias( user.getAlias() );

        return userRedis;
    }

    @Override
    public Object toDTO(UserDTO userDTO) {
        if ( userDTO == null ) {
            return null;
        }

        Object object = new Object();

        return object;
    }
}
