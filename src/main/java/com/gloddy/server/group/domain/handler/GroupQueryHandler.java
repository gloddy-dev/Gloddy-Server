package com.gloddy.server.group.domain.handler;

import com.gloddy.server.group.domain.Group;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GroupQueryHandler {

    Group findById(Long id);

    Page<Group> findGroupPage(String school, Pageable pageable);
}
