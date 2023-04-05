package com.gloddy.server.group.repository;

import com.gloddy.server.group.entity.UserGroup;
import com.gloddy.server.group.repository.custom.UserGroupJpaRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserGroupJpaRepository extends JpaRepository<UserGroup, Long>, UserGroupJpaRepositoryCustom {
    Optional<UserGroup> findByUserIdAndGroupId(Long userId, Long groupId);
}
