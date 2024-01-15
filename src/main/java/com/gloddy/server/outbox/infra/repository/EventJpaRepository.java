package com.gloddy.server.outbox.infra.repository;

import com.gloddy.server.outbox.domain.Event;
import com.gloddy.server.outbox.infra.repository.custom.EventJpaRepositoryCustom;
import java.time.LocalDateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EventJpaRepository extends JpaRepository<Event, Long>, EventJpaRepositoryCustom {

    @Modifying
    @Query(value = "UPDATE group_event e SET e.published = true, e.published_at = :publishedAt where e.id = :id", nativeQuery = true)
    void updatePublished(@Param("id") Long id, @Param("publishedAt") LocalDateTime publishedAt);
}
