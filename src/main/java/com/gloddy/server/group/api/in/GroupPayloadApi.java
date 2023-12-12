package com.gloddy.server.group.api.in;

import com.gloddy.server.core.response.ApiResponse;
import com.gloddy.server.group.application.GroupPayloadService;
import com.gloddy.server.group.domain.dto.GroupMemberPayload;
import com.gloddy.server.group.domain.dto.GroupPayload;
import com.gloddy.server.group.domain.vo.in.GroupMemberPayloadEventType;
import com.gloddy.server.group.domain.vo.in.GroupPayloadEventType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/internal/payload")
public class GroupPayloadApi {
    private final GroupPayloadService groupPayloadService;

    @GetMapping("/groups/{groupId}")
    public ResponseEntity<GroupPayload> getGroupPayload(
            @PathVariable("groupId") Long groupId,
            @RequestParam("eventType") GroupPayloadEventType eventType
    ) {
        GroupPayload response = groupPayloadService.getGroupPayload(groupId, eventType);
        return ApiResponse.ok(response);
    }

    @GetMapping("/groups/{groupId}/members/{userId}")
    public ResponseEntity<GroupMemberPayload> getGroupMemberPayload(
            @PathVariable("groupId") Long groupId,
            @PathVariable("userId") Long userId,
            @RequestParam("eventType") GroupMemberPayloadEventType eventType
    ) {
        GroupMemberPayload response = groupPayloadService.getGroupMemberPayload(groupId, userId, eventType);
        return ApiResponse.ok(response);
    }
}
