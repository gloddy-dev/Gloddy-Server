package com.gloddy.server.group.domain.service;

import com.gloddy.server.auth.domain.User;
import com.gloddy.server.group.domain.Group;
import com.gloddy.server.group.domain.dto.GroupRequest;

public interface GroupFactory {

    Group getGroupFrom(User captain, GroupRequest.Create request);
}
