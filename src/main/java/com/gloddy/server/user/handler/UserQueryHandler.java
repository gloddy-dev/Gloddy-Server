package com.gloddy.server.user.handler;

import com.gloddy.server.auth.entity.User;

public interface UserQueryHandler {
    User findById(Long id);
    User findByEmail(String email);
}
