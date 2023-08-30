package com.gloddy.server.myGroup.read;

import com.gloddy.server.core.response.ApiResponse;
import com.gloddy.server.myGroup.read.dto.MyGroupResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class MyGroupReadController {

    private final MyGroupReadService myGroupReadService;

    @GetMapping("/users/groups/participating")
    public ResponseEntity<MyGroupResponse.Participating> getParticipatingGroups(@AuthenticationPrincipal Long userId) {
        MyGroupResponse.Participating response = myGroupReadService.getParticipatingGroups(userId);
        return ApiResponse.ok(response);
    }

    @GetMapping("/users/groups/hosting")
    public ResponseEntity<MyGroupResponse.Hosting> getHostingGroups(@AuthenticationPrincipal Long userId) {
        MyGroupResponse.Hosting response = myGroupReadService.getHostingGroups(userId);
        return ApiResponse.ok(response);
    }

    @GetMapping("/users/groups/waiting")
    public ResponseEntity<MyGroupResponse.Waiting> getWaitingGroups(@AuthenticationPrincipal Long userId) {
        MyGroupResponse.Waiting response = myGroupReadService.getWaitingGroups(userId);
        return ApiResponse.ok(response);
    }

    @GetMapping("/users/groups/rejected")
    public void getRejectedGroups(@AuthenticationPrincipal Long userId) {

    }
}
