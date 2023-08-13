package com.gloddy.server.core.api;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class HealthCheckController {

    @Operation(hidden = true)
    @GetMapping("/health")
    public String healthCheck() {
        return "healthy";
    }
}
