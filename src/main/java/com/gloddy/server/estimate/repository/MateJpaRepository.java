package com.gloddy.server.estimate.repository;

import com.gloddy.server.estimate.entity.Mate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MateJpaRepository extends JpaRepository<Mate, Long> {
}
