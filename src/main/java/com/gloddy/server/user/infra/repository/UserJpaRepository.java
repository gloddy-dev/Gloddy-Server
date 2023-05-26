package com.gloddy.server.user.infra.repository;

import com.gloddy.server.auth.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    User findFirstByOrderByIdDesc();
}
