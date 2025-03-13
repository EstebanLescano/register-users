package com.dmh.registerusers.postgresrepository;

import com.dmh.registerusers.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserPostgresRepository extends JpaRepository<UserEntity, String> {

    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findById(String id);

    Optional<UserEntity> findByPassword(String password);

    void deleteById(String id);
}
