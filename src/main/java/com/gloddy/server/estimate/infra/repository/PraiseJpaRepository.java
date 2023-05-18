package com.gloddy.server.estimate.infra.repository;

import com.gloddy.server.estimate.domain.Praise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PraiseJpaRepository extends JpaRepository<Praise, Long> {
    Optional<Praise> findByUserId(Long userId);
}