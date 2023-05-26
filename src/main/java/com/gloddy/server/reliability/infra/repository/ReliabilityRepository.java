package com.gloddy.server.reliability.infra.repository;

import com.gloddy.server.reliability.domain.Reliability;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReliabilityRepository extends JpaRepository<Reliability, Long> {
    Optional<Reliability> findByUserId(Long userId);
}
