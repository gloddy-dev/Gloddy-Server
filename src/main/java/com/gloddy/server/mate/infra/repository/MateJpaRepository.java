package com.gloddy.server.mate.infra.repository;

import com.gloddy.server.mate.domain.Mate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface MateJpaRepository extends JpaRepository<Mate, Long> {
    Long countByUserId(Long userId);
    List<Mate> findAllByUserId(Long userId);
    @Transactional
    void deleteByMateIdAndUserId(Long mateId, Long userId);
}
