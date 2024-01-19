package com.gloddy.server.user.infra.repository.custom;

import com.gloddy.server.user.application.internal.dto.UserPreviewResponse;
import com.gloddy.server.user.domain.User;
import com.gloddy.server.user.domain.vo.Phone;
import com.gloddy.server.user.domain.vo.kind.Status;
import com.gloddy.server.user.domain.dto.PraiseResponse;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserJpaRepositoryCustom {

    Optional<User> findByPhone(Phone phone);

    Optional<User> findByEmail(String email);

    Optional<User> findByIdFetch(Long id);

    PraiseResponse.GetPraiseForUser findPraiseByUserId(Long userId);

    Optional<UserPreviewResponse> findUserPreviewById(Long id);

    List<UserPreviewResponse> findUserPreviewsByInId(Collection<Long> ids);
}
