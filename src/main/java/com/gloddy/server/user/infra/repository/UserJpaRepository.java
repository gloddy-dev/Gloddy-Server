package com.gloddy.server.user.infra.repository;

import com.gloddy.server.user.domain.User;
import com.gloddy.server.user.infra.repository.custom.UserJpaRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<User, Long>, UserJpaRepositoryCustom {
    User findFirstByOrderByIdDesc();

    boolean existsByProfile_Nickname(String nickName);
}
