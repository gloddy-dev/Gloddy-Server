package com.gloddy.server.user.domain.handler;

import com.gloddy.server.user.domain.User;

public interface UserCommandHandler {

    User save(User user);
}
