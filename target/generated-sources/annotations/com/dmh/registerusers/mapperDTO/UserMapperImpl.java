package com.dmh.registerusers.mapperdto;

import com.dmh.registerusers.entity.UserEntity;
import com.dmh.registerusers.entityredis.UserRedis;
import com.dmh.registerusers.model.UserDTO;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-13T07:56:34-0300",
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

        userDTO.setId( entity.getId() );
        userDTO.setNombreyapellido( entity.getNombreyapellido() );
        userDTO.setDni( entity.getDni() );
        userDTO.setEmail( entity.getEmail() );
        userDTO.setTelefono( entity.getTelefono() );
        userDTO.setPassword( entity.getPassword() );
        userDTO.setCvu( entity.getCvu() );
        userDTO.setAlias( entity.getAlias() );
        if ( entity.getCreatedAt() != null ) {
            userDTO.setCreatedAt( DateTimeFormatter.ISO_LOCAL_DATE_TIME.format( entity.getCreatedAt() ) );
        }

        return userDTO;
    }

    @Override
    public UserEntity toEntity(UserDTO user) {
        if ( user == null ) {
            return null;
        }

        UserEntity userEntity = new UserEntity();

        userEntity.setId( user.getId() );
        userEntity.setNombreyapellido( user.getNombreyapellido() );
        userEntity.setDni( user.getDni() );
        userEntity.setEmail( user.getEmail() );
        userEntity.setTelefono( user.getTelefono() );
        userEntity.setPassword( user.getPassword() );
        userEntity.setCvu( user.getCvu() );
        userEntity.setAlias( user.getAlias() );
        if ( user.getCreatedAt() != null ) {
            userEntity.setCreatedAt( LocalDateTime.parse( user.getCreatedAt() ) );
        }

        return userEntity;
    }

    @Override
    public UserDTO toDTO(UserRedis redis) {
        if ( redis == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( redis.getId() );
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

        userRedis.setId( user.getId() );
        userRedis.setNombreyapellido( user.getNombreyapellido() );
        userRedis.setDni( user.getDni() );
        userRedis.setEmail( user.getEmail() );
        userRedis.setTelefono( user.getTelefono() );
        userRedis.setPassword( user.getPassword() );
        userRedis.setCvu( user.getCvu() );
        userRedis.setAlias( user.getAlias() );

        return userRedis;
    }
}
