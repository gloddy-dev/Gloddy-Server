package com.gloddy.server.apply.infra.repository.custom;

import com.gloddy.server.apply.domain.Apply;
import com.gloddy.server.apply.domain.vo.Status;

import java.util.List;
import java.util.Optional;

public interface ApplyJpaRepositoryCustom {

    Optional<Apply> findByIdFetchGroupAndCaptain(Long id);

    List<Apply> findAllByGroupIdAndStatus(Long groupId, Status status);

    List<Apply> findAllByUserIdAndStatus(Long userId, Status status);

    Optional<Apply> findByIdFetchUserAndGroup(Long id);
}
