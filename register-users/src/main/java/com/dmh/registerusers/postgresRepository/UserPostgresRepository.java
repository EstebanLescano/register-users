package com.dmh.registerusers.postgresRepository;

import com.dmh.registerusers.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPostgresRepository extends JpaRepository<UserEntity, String> {
}
