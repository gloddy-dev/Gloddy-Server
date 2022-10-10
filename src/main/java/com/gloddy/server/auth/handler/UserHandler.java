package com.gloddy.server.auth.handler;

import com.gloddy.server.auth.entity.User;

public interface UserHandler {
    User findById(Long id);
    User findByEmail(String email);
}
