package com.gloddy.server.group.repository;

import com.gloddy.server.group.entity.UserGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserGroupJpaRepository extends JpaRepository<UserGroup, Long> {
}
