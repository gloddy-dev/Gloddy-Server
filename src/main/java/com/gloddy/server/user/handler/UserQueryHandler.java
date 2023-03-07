package com.gloddy.server.user.handler;

import com.gloddy.server.auth.entity.User;

public interface UserHandler {
    User findById(Long id);
    User findByEmail(String email);
}
