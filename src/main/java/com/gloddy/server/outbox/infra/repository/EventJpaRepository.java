package com.gloddy.server.outbox.infra.repository;

import com.gloddy.server.outbox.domain.Event;
import com.gloddy.server.outbox.infra.repository.custom.EventJpaRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventJpaRepository extends JpaRepository<Event, Long>, EventJpaRepositoryCustom {

}
