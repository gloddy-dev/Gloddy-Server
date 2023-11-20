package com.gloddy.server.user.infra.repository.custom;

import com.gloddy.server.user.domain.User;
import com.gloddy.server.user.domain.vo.Phone;
import com.gloddy.server.user.domain.vo.kind.Status;
import com.gloddy.server.user.domain.dto.PraiseResponse;

import java.util.Optional;

public interface UserJpaRepositoryCustom {

    Optional<User> findByPhone(Phone phone);

    Optional<User> findByEmail(String email);

    Optional<User> findByIdAndStatusFetch(Long id, Status status);

    PraiseResponse.GetPraiseForUser findPraiseByUserId(Long userId);
}
