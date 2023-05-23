package com.gloddy.server.user_group.infra.repository;

import com.gloddy.server.user_group.domain.UserGroup;
import com.gloddy.server.user_group.infra.repository.custom.UserGroupJpaRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserGroupJpaRepository extends JpaRepository<UserGroup, Long>, UserGroupJpaRepositoryCustom {
    Optional<UserGroup> findByUserIdAndGroupId(Long userId, Long groupId);

    UserGroup findFirstByOrderByIdDesc();
}
