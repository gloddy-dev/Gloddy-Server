package com.gloddy.server.group.infra.repository;

import com.gloddy.server.group.domain.Group;
import com.gloddy.server.group.infra.repository.custom.GroupJpaRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface GroupJpaRepository extends JpaRepository<Group, Long>, GroupJpaRepositoryCustom {
    Page<Group> findAllByOrderByIdDesc(Pageable pageable);

    Group findFirstByOrderByIdDesc();
}
