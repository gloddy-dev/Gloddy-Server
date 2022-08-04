package com.gloddy.server.auth.repository;

import com.gloddy.server.auth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
}
