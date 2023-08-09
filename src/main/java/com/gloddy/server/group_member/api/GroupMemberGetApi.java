package com.gloddy.server.group_member.api;

import com.gloddy.server.core.response.ApiResponse;
import com.gloddy.server.group_member.api.dto.GroupMemberResponse;
import com.gloddy.server.group_member.application.GroupMemberService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.gloddy.server.group_member.api.dto.GroupMemberResponse.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class GroupMemberGetApi {

    private final GroupMemberService groupMemberService;

    @Operation(summary = "그룹 멤버 조회")
    @GetMapping("/groups/{groupId}/members")
    public ResponseEntity<GetAll> getGroupMembers(@PathVariable("groupId") Long groupId) {
        GetAll response = groupMemberService.getGroupMembers(groupId);
        return ApiResponse.ok(response);
    }
}
