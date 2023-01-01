package com.gloddy.server.apply.repository;

import com.gloddy.server.apply.entity.Apply;
import com.gloddy.server.apply.entity.vo.Status;
import com.gloddy.server.group.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

import java.util.Optional;

public interface ApplyJpaRepository extends JpaRepository<Apply, Long> {
    int countApplyByGroupIdAndStatus(Long group_id, Status status);

    Optional<Apply> findByUserIdAndGroupId(Long userId, Long groupId);

    @Query("select a from Apply a join fetch a.user where a.group = :group and a.status = :status")
    List<Apply> findAppliesByGroupAndStatusFetchUser(@Param("group") Group group, @Param("status") Status status);

    Long countByGroupIdAndStatus(Long groupId, Status status);
}
