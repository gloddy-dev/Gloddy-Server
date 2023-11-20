package com.gloddy.server.user.domain.handler;

import com.gloddy.server.user.domain.User;
import com.gloddy.server.user.domain.vo.Phone;
import com.gloddy.server.user.domain.vo.kind.Status;
import com.gloddy.server.user.domain.dto.PraiseResponse;

import java.util.Optional;

public interface UserQueryHandler {
    User findById(Long id);

    User findByIdAndStatus(Long id, Status status);
    Optional<User> findByEmail(String email);

    boolean existsByNickname(String nickname);

    Optional<User> findByPhone(Phone phone);

    PraiseResponse.GetPraiseForUser findPraiseDtoByUserId(Long userId);
}
