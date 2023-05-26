package com.gloddy.server.estimate.infra.repository;

import com.gloddy.server.estimate.domain.Mate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MateJpaRepository extends JpaRepository<Mate, Long> {
    Long countByMateId(Long mateId);
    List<Mate> findAllByUserId(Long userId);
    void deleteByMateIdAndUserId(Long mateId, Long userId);
}
