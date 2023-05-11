package com.gloddy.server.group.domain.handler;

import com.gloddy.server.group.domain.Group;

public interface GroupQueryHandler {

    Group findById(Long id);
}
