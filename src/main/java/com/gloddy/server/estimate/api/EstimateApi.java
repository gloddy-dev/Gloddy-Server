package com.gloddy.server.estimate.api;

import com.gloddy.server.core.response.ApiResponse;
import com.gloddy.server.estimate.dto.EstimateRequest;
import com.gloddy.server.estimate.service.EstimateService;
import com.gloddy.server.estimate.service.GetGroupMemberForEstimateService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static com.gloddy.server.estimate.dto.EstimateResponse.*;
import static java.util.stream.Collectors.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class EstimateApi {
    private final GetGroupMemberForEstimateService getGroupMemberForEstimateService;
    private final EstimateService estimateService;

    @ApiOperation("칭찬 할 멤버 조회")
    @GetMapping("/groups/{groupId}/estimate")
    public GetGroupMembers getGroupMembersForEstimate(@PathVariable("groupId") Long groupId,
                                                      @AuthenticationPrincipal Long userId) {
        return getGroupMemberForEstimateService.getGroupMembers(userId, groupId)
                .stream()
                .map(GetGroupMembers.GetGroupMember::from)
                .collect(collectingAndThen(toList(), GetGroupMembers::new));
    }

    @ApiOperation("팀원 평가 - 칭찬 + 최고의 짝꿍")
    @PostMapping("/groups/{groupId}/estimate")
    public ResponseEntity<Void> estimateInGroup(@AuthenticationPrincipal Long userId,
                                                @PathVariable("groupId") Long groupId,
                                                @RequestBody EstimateRequest estimateRequest) {
        estimateService.estimateInGroup(userId, groupId, estimateRequest);
        return ApiResponse.noContent();
    }
}
