package com.gloddy.server.apply.infra.repository;

import com.gloddy.server.apply.domain.Apply;
import com.gloddy.server.apply.domain.vo.Status;
import com.gloddy.server.apply.infra.repository.custom.ApplyJpaRepositoryCustom;
import com.gloddy.server.user.domain.User;
import com.gloddy.server.group.domain.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

import java.util.Optional;

public interface ApplyJpaRepository extends JpaRepository<Apply, Long>, ApplyJpaRepositoryCustom {
    int countApplyByGroupIdAndStatus(Long group_id, Status status);

    Optional<Apply> findByUserIdAndGroupId(Long userId, Long groupId);

    @Query("select a from Apply a join fetch a.user where a.group = :group and a.status = :status")
    List<Apply> findAppliesByGroupAndStatusFetchUser(@Param("group") Group group, @Param("status") Status status);

    Long countByGroupIdAndStatus(Long groupId, Status status);

    Apply findFirstByOrderByIdDesc();

    Boolean existsByUserIdAndGroupIdAndStatus(Long userId, Long groupId, Status status);

    Boolean existsByUserAndStatus(User user, Status status);

    Boolean existsByGroupIdAndStatus(Long groupId, Status status);
}
