package com.gloddy.server.user_group.api;

import com.gloddy.server.core.response.PageResponse;
import com.gloddy.server.group.domain.dto.GroupResponse;
import com.gloddy.server.user_group.application.UserGroupService;
import io.swagger.annotations.ApiOperation;
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

    private final UserGroupService userGroupService;

    @ApiOperation("내가 참여할 그룹 조회 - 나의 모임 윗부분 - paging처리 x")
    @GetMapping("/groups/my-expected")
    public GroupResponse.GetGroups getExpectedMyGroup(@AuthenticationPrincipal Long userId) {
        return userGroupService.getExpectedMyGroup(userId);
    }

    @ApiOperation("내가 참여했던 그룹 조회 - 나의 모임 아랫 부분 - paging처리 o")
    @GetMapping("/groups/my-participated")
    public PageResponse<GroupResponse.GetParticipatedGroup> getParticipatedMyGroup(
            @AuthenticationPrincipal Long userId,
            @RequestParam int page,
            @RequestParam int size) {
        return userGroupService.getParticipatedMyGroup(userId, page, size);
    }
}
