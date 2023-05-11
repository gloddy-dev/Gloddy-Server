package com.gloddy.server.apply.domain.handler;

import com.gloddy.server.apply.domain.Apply;
import com.gloddy.server.group.domain.Group;

import java.util.List;


public interface ApplyQueryHandler {

    List<Apply> findAllApprovedAppliesFetchUserBy(Group group);

    Long countApprovedAppliesBy(Long groupId);
}
