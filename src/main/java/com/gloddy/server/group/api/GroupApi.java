package com.gloddy.server.group.api;

import com.gloddy.server.core.response.ApiResponse;
import com.gloddy.server.group.dto.GroupRequest;
import com.gloddy.server.group.dto.GroupResponse;
import com.gloddy.server.group.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GroupApi {

    private final GroupService groupService;

    @PostMapping("/group-create")
    public ResponseEntity createGroup(@AuthenticationPrincipal Long captainId,
                                      @RequestBody GroupRequest.Create req) {
        GroupResponse.Create response = groupService.createGroup(captainId, req);
        return ApiResponse.ok(response);
    }
}
