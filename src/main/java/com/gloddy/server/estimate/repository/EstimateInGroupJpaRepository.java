package com.gloddy.server.estimate.repository;

import com.gloddy.server.estimate.entity.EstimateInGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstimateInGroupJpaRepository extends JpaRepository<EstimateInGroup, Long> {
}
