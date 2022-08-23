package com.gloddy.server.group.repository;

import com.gloddy.server.group.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupJpaRepository extends JpaRepository<Group, Long> {
}
