package com.gloddy.server.apply.repository;

import com.gloddy.server.apply.entity.Apply;
import com.gloddy.server.apply.entity.vo.Status;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplyJpaRepository extends JpaRepository<Apply, Long> {
    int countApplyByGroupIdAndStatus(Long group_id, Status status);
}
