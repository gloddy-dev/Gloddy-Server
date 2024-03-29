package com.gloddy.server.scrap.infra.repository;

import com.gloddy.server.user.domain.User;
import com.gloddy.server.group.domain.Group;
import com.gloddy.server.scrap.domain.Scrap;
import com.gloddy.server.scrap.infra.repository.custom.ScrapJpaRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScrapJpaRepository extends JpaRepository<Scrap, Long>, ScrapJpaRepositoryCustom {
    Page<Scrap> findByUser(User user, Pageable pageable);
    Boolean existsByUserAndGroup(User user, Group group);

    void deleteByUserAndGroup(User user, Group group);
}
