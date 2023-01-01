package com.gloddy.server.estimate.repository;

import com.gloddy.server.estimate.entity.AbsenceInGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstimateInGroupJpaRepository extends JpaRepository<AbsenceInGroup, Long> {
}
