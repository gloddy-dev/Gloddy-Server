package com.gloddy.server.estimate.repository;

import com.gloddy.server.auth.entity.User;
import com.gloddy.server.estimate.entity.AbsenceInGroup;
import com.gloddy.server.group.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AbsenceInGroupJpaRepository extends JpaRepository<AbsenceInGroup, Long> {

    Optional<AbsenceInGroup> findByGroupIdAndUserId(Long groupId, Long userId);
}
