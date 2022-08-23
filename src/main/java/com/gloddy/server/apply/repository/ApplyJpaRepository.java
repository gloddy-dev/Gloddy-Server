package com.gloddy.server.apply.repository;

import com.gloddy.server.apply.entity.Apply;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplyJpaRepository extends JpaRepository<Apply, Long> {
}
