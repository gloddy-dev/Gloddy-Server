package com.gloddy.server.user.infra.repository;

import com.gloddy.server.auth.domain.User;
import com.gloddy.server.user.infra.repository.custom.UserJpaRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<User, Long>, UserJpaRepositoryCustom {
    User findFirstByOrderByIdDesc();

    boolean existsByProfile_Nickname(String nickName);
}
