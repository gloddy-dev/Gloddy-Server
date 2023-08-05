package com.gloddy.server.core.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Slf4j
@RestController
@RequiredArgsConstructor
public class HealthCheckController {

    private final DataSource dataSource;

    @GetMapping("/health")
    public void healthCheck() {
        try {
            Connection conn = dataSource.getConnection();
            log.info("connection: {}", conn.toString());
        } catch (SQLException e) {
            log.error("Health check database connection error", e);
        }
    }
}
