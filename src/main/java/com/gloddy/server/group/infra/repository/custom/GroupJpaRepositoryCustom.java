package com.gloddy.server.group.infra.repository.custom;

import com.gloddy.server.group.domain.Group;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface GroupJpaRepositoryCustom {

    List<Group> findAllByCaptainId(Long captainId);

    Page<Group> findAllByEndDateTimeAfterOrderByCreatedAtDesc(LocalDateTime time, Pageable pageable);
}
