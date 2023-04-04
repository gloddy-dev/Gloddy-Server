package com.gloddy.server.group.api;

import com.gloddy.server.core.response.PageResponse;
import com.gloddy.server.group.dto.GroupResponse;
import com.gloddy.server.group.service.MyGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class UserGroupApi {

    private final MyGroupService myGroupService;

    @GetMapping("/groups/my-expected")
    public GroupResponse.GetGroups getExpectedMyGroup(@AuthenticationPrincipal Long userId) {
        return myGroupService.getExpectedMyGroup(userId);
    }

    @GetMapping("/groups/my-participated")
    public PageResponse<GroupResponse.GetGroup> getParticipatedMyGroup(
            @AuthenticationPrincipal Long userId,
            @RequestParam int page,
            @RequestParam int size) {
        return myGroupService.getParticipatedMyGroup(userId, page, size);
    }
}
