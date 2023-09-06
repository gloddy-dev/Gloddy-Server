package com.gloddy.server.user.domain.handler;

import com.gloddy.server.auth.domain.User;
import com.gloddy.server.auth.domain.vo.Phone;
import com.gloddy.server.auth.domain.vo.kind.Status;

import java.util.Optional;

public interface UserQueryHandler {
    User findById(Long id);

    User findByIdAndStatus(Long id, Status status);
    Optional<User> findByEmail(String email);

    boolean existsByNickname(String nickname);

    Optional<User> findByPhone(Phone phone);
}
