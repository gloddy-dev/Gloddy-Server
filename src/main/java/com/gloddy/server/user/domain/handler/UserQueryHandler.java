package com.gloddy.server.user.domain.handler;

import com.gloddy.server.user.application.internal.dto.UserPreviewResponse;
import com.gloddy.server.user.application.internal.dto.UserPreviewsResponse;
import com.gloddy.server.user.domain.User;
import com.gloddy.server.user.domain.vo.Phone;
import com.gloddy.server.user.domain.dto.PraiseResponse;

import java.util.Collection;
import java.util.Optional;

public interface UserQueryHandler {
    User findById(Long id);

    User findByIdFetch(Long id);
    Optional<User> findByEmail(String email);

    boolean existsByNickname(String nickname);

    Optional<User> findByPhone(Phone phone);

    PraiseResponse.GetPraiseForUser findPraiseDtoByUserId(Long userId);

    UserPreviewResponse findUserPreviewById(Long userId);

    UserPreviewsResponse findUserPreviewsByInId(Collection<Long> userIds);
}
