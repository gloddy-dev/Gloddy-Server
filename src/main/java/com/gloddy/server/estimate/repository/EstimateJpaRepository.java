package com.gloddy.server.estimate.repository;

import com.gloddy.server.estimate.entity.EstimateInGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstimateJpaRepository extends JpaRepository<EstimateInGroup, Long> {
}
