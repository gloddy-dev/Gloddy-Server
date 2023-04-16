package com.gloddy.server.estimate.repository;

import com.gloddy.server.estimate.entity.UserGroupAbsence;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserGroupAbsenceJpaRepository extends JpaRepository<UserGroupAbsence, Long> {

    Optional<UserGroupAbsence> findByGroupIdAndUserId(Long groupId, Long userId);

    UserGroupAbsence findFirstByOrderByIdDesc();
}
