package com.gloddy.server.estimate.api;

import com.gloddy.server.estimate.service.GetGroupMemberForEstimateService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.gloddy.server.estimate.dto.EstimateResponse.*;
import static java.util.stream.Collectors.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class EstimateApi {
    private final GetGroupMemberForEstimateService getGroupMemberForEstimateService;

    @ApiOperation("칭찬 할 멤버 조회")
    @GetMapping("/groups/{groupId}/praise")
    public GetGroupMembers getGroupMembersForEstimate(@PathVariable("groupId") Long groupId,
                                                                       @AuthenticationPrincipal Long userId) {
        return getGroupMemberForEstimateService.getGroupMembers(userId, groupId)
                .stream()
                .map(GetGroupMembers.GetGroupMember::from)
                .collect(collectingAndThen(toList(), GetGroupMembers::new));
    }
}
