package com.gloddy.server.user.domain.handler;

import com.gloddy.server.auth.domain.User;

public interface UserQueryHandler {
    User findById(Long id);
    User findByEmail(String email);
}
