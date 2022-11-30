package com.gloddy.server.apply.handler;

import com.gloddy.server.apply.entity.Apply;
import com.gloddy.server.group.entity.Group;

import java.util.List;

public interface ApplyQueryHandler {

    List<Apply> findAllApprovedAppliesFetchUserBy(Group group);
}
