package com.gloddy.server.group.infra.repository.custom;

import com.gloddy.server.group.domain.Group;

import java.util.List;

public interface GroupJpaRepositoryCustom {

    List<Group> findAllByCaptainId(Long captainId);
}
