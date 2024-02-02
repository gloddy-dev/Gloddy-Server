package com.gloddy.server.outbox.domain;

import com.vladmihalcea.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "user_event")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UserEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Type(JsonType.class)
    @Column(name = "event", columnDefinition = "json")
    private Map<String, Object> event = new HashMap<>();

    @Column(name = "event_type", nullable = false)
    private String eventType;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    private boolean published;

    @Column(name = "published_at")
    private LocalDateTime publishedAt;

    public UserEvent(Map<String, Object> event, String eventType) {
        this.event = event;
        this.eventType = eventType;
        this.createdAt = LocalDateTime.now();
        this.published = false;
    }
}
