package com.gloddy.server.group_member.api;

import com.gloddy.server.core.response.ApiResponse;
import com.gloddy.server.group_member.application.GroupMemberDeleteService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class GroupMemberDeleteApi {

    private final GroupMemberDeleteService groupMemberDeleteService;

    @Operation(description = "모임 나가기")
    @DeleteMapping("/groups/{groupId}/members")
    public ResponseEntity<Void> deleteGroupMember(
            @PathVariable Long groupId,
            @AuthenticationPrincipal Long userId
    ) {
        groupMemberDeleteService.delete(userId, groupId);
        return ApiResponse.noContent();
    }

}
