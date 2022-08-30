package com.gloddy.server.group.api;

import com.gloddy.server.core.response.PageResponse;
import com.gloddy.server.group.dto.response.GetGroupResponse;
import com.gloddy.server.group.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/api")
@RequiredArgsConstructor
public class GroupController {
    private final GroupService groupService;

    @GetMapping("/groups")
    public PageResponse<GetGroupResponse.GetGroup> getGroups(
            @AuthenticationPrincipal Long userId,
            @RequestParam int size,
            @RequestParam int page
    ) {
        return groupService.getGroups(userId, size, page);
    }
}
