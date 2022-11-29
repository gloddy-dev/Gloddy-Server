package com.gloddy.server.group.handler;

import com.gloddy.server.group.entity.Group;

public interface GroupHandler {
    Group findById(Long id);
}
