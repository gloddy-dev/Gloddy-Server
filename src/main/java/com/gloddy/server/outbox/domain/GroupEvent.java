package com.gloddy.server.outbox.domain;

import com.vladmihalcea.hibernate.type.json.JsonType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

@Getter
@Entity
@Table(name = "group_event")
@NoArgsConstructor
@AllArgsConstructor
public class GroupEvent {

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

    public GroupEvent(Map<String, Object> event, String eventType) {
        this.event = event;
        this.eventType = eventType;
        this.createdAt = LocalDateTime.now();
        this.published = false;
    }
}
