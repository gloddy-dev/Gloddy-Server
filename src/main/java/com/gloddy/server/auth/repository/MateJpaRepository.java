package com.gloddy.server.auth.repository;


import com.gloddy.server.auth.entity.Mate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MateJpaRepository extends JpaRepository<Mate, Long> {
}
