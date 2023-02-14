package com.gloddy.server.scrap.repository;

import com.gloddy.server.auth.entity.User;
import com.gloddy.server.group.entity.Group;
import com.gloddy.server.scrap.entity.Scrap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScrapJpaRepository extends JpaRepository<Scrap, Long> {
    Page<Scrap> findByUser(User user, Pageable pageable);
    void deleteByUserAndGroup(User user, Group group);
}
