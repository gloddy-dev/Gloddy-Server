package com.gloddy.server.apply.api.in;

import com.gloddy.server.apply.application.ApplyPayloadService;
import com.gloddy.server.apply.domain.dto.ApplyPayload;
import com.gloddy.server.messaging.adapter.apply.event.ApplyEventType;
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
public class ApplyPayloadApi {
    private final ApplyPayloadService applyPayloadService;

    @GetMapping("/applies/{applyId}")
    public ResponseEntity<ApplyPayload> getApplyPayload(
            @PathVariable("applyId") Long applyId,
            @RequestParam("eventType") ApplyEventType eventType
    ) {
        ApplyPayload response = applyPayloadService.getPayload(applyId, eventType);
        return ResponseEntity.ok(response);
    }
}
