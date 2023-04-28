package com.gloddy.server.reliability.repository;

import com.gloddy.server.auth.entity.User;
import com.gloddy.server.reliability.entity.Reliability;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReliabilityRepository extends JpaRepository<Reliability, Long> {
    Optional<Reliability> findByUserId(Long userId);
}
