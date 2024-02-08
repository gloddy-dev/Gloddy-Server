package com.gloddy.server.outbox.infra.repository;

import com.gloddy.server.outbox.domain.UserEvent;
import com.gloddy.server.outbox.infra.repository.custom.UserEventJpaRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface UserEventJpaRepository extends JpaRepository<UserEvent, Long>, UserEventJpaRepositoryCustom {

    @Modifying
    @Query(value = "UPDATE user_event ue SET ue.published = true, ue.published_at = :publishedAt where ue.id = :id", nativeQuery = true)
    void updatePublished(@Param("id") Long id, @Param("publishedAt") LocalDateTime publishedAt);
}
