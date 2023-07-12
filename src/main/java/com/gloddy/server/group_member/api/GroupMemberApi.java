package com.gloddy.server.group_member.api;

import com.gloddy.server.core.response.ApiResponse;
import com.gloddy.server.core.response.PageResponse;
import com.gloddy.server.group_member.domain.dto.EstimateResponse;
import com.gloddy.server.group_member.application.GetGroupMemberForEstimateService;
import com.gloddy.server.group.domain.dto.GroupResponse;
import com.gloddy.server.group_member.application.GroupMemberService;
import com.gloddy.server.group_member.domain.dto.GroupMemberRequest;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class GroupMemberApi {

    private final GroupMemberService groupMemberService;
    private final GetGroupMemberForEstimateService getGroupMemberForEstimateService;

    @Operation(summary = "내가 참여할 그룹 조회 - 나의 모임 윗부분 - paging처리 x")
    @GetMapping("/groups/my-expected")
    public GroupResponse.GetGroups getExpectedMyGroup(@AuthenticationPrincipal Long userId) {
        return groupMemberService.getExpectedMyGroup(userId);
    }

    @Operation(summary = "내가 참여했던 그룹 조회 - 나의 모임 아랫 부분 - paging처리 o")
    @GetMapping("/groups/my-participated")
    public PageResponse<GroupResponse.GetParticipatedGroup> getParticipatedMyGroup(
            @AuthenticationPrincipal Long userId,
            @RequestParam int page,
            @RequestParam int size) {
        return groupMemberService.getParticipatedMyGroup(userId, page, size);
    }

    @Operation(summary = "칭찬 할 멤버 조회")
    @GetMapping("/groups/{groupId}/estimate")
    public EstimateResponse.GetGroupMembers getGroupMembersForEstimate(@PathVariable("groupId") Long groupId,
                                                                       @AuthenticationPrincipal Long userId) {
        return getGroupMemberForEstimateService.getGroupMembers(userId, groupId)
                .stream()
                .map(EstimateResponse.GetGroupMembers.GetGroupMember::from)
                .collect(collectingAndThen(toList(), EstimateResponse.GetGroupMembers::new));
    }

    @Operation(summary = "팀원 평가 - 칭찬 + 최고의 짝꿍")
    @PostMapping("/groups/{groupId}/group_members/estimate")
    public ResponseEntity<Void> estimateGroupMembers(@AuthenticationPrincipal Long userId,
                                                     @PathVariable("groupId") Long groupId,
                                                     @RequestBody GroupMemberRequest.Estimate request) {
        groupMemberService.estimateGroupMembers(request, userId, groupId);
        return ApiResponse.noContent();
    }
}
