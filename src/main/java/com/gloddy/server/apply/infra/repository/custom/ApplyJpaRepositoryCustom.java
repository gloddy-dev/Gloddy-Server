package com.gloddy.server.apply.infra.repository.custom;

import com.gloddy.server.apply.domain.Apply;

import java.util.Optional;

public interface ApplyJpaRepositoryCustom {

    Optional<Apply> findByIdFetchGroupAndCaptain(Long id);
}
