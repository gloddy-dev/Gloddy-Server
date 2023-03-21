package com.gloddy.server.group.api;

import com.gloddy.server.group.dto.GroupResponse;
import com.gloddy.server.group.service.MyGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class UserGroupApi {

    private final MyGroupService myGroupService;

    @GetMapping("/groups/my-expected")
    public List<GroupResponse.GetGroup> getExpectedMyGroup(@AuthenticationPrincipal Long userId) {
        return myGroupService.getExpectedMyGroup(userId);
    }
}
