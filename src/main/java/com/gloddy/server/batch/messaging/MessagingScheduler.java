package com.gloddy.server.batch.messaging;

import com.gloddy.server.batch.messaging.producer.MessagingBatchEventProducer;
import com.gloddy.server.core.error.handler.exception.BatchBusinessException;
import com.gloddy.server.outbox.domain.handler.GroupOutboxEventQueryHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@EnableScheduling
@RequiredArgsConstructor
public class MessagingScheduler {

    private final MessagingBatchEventProducer messagingBatchEventProducer;
    private final GroupOutboxEventQueryHandler groupOutboxEventQueryHandler;

    @Transactional
    @Scheduled(cron = "0 * * * * *", zone = "Asia/Seoul")
    public void rePublishEvent() {
        executeJob(() -> groupOutboxEventQueryHandler.findAllByNotPublished()
                .forEach(messagingBatchEventProducer::produceEvent));
    }

    private void executeJob(Runnable runnable) {
        log.info("Messaging rePublishEvent publishGroupEndEvent 스케줄러 시작");
        try {
            runnable.run();
        } catch (Exception e) {
            throw new BatchBusinessException(e);
        }
        log.info("Messaging rePublishEvent publishGroupEndEvent 스케줄러 완료");
    }
}
