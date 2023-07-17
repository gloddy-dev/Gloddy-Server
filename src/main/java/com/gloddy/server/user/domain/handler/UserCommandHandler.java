package com.gloddy.server.user.domain.handler;

import com.gloddy.server.auth.domain.User;

public interface UserCommandHandler {

    User save(User user);
}
