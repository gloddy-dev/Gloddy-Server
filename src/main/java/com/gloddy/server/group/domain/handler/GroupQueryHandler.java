package com.gloddy.server.group.domain.handler;

import com.gloddy.server.group.domain.Group;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface GroupQueryHandler {

    Group findById(Long id);

    Page<Group> findGroupPreviewPage(Pageable pageable);

    List<Group> findAllByCaptainId(Long captainId);

    List<Group> findAllScrappedGroups(Long userId);
}
