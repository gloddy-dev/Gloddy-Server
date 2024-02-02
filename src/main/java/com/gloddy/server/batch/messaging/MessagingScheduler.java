package com.gloddy.server.batch.messaging;

import com.gloddy.server.batch.messaging.producer.MessagingBatchEventProducer;
import com.gloddy.server.core.error.handler.exception.BatchBusinessException;
import com.gloddy.server.outbox.domain.handler.GroupOutboxEventQueryHandler;
import com.gloddy.server.outbox.domain.handler.UserOutboxEventQueryHandler;
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
    private final UserOutboxEventQueryHandler userOutboxEventQueryHandler;

    @Transactional
    @Scheduled(cron = "0 * * * * *", zone = "Asia/Seoul")
    public void rePublishGroupEvent() {
        executeJob(() -> groupOutboxEventQueryHandler.findAllByNotPublished()
                .forEach(messagingBatchEventProducer::produceEvent), "group");
    }

    @Transactional
    @Scheduled(cron = "0 * * * * *", zone = "Asia/Seoul")
    public void rePublishUserEvent() {
        executeJob(() -> userOutboxEventQueryHandler.findAllByNotPublished()
                .forEach(messagingBatchEventProducer::produceEvent), "user");
    }

    private void executeJob(Runnable runnable, String domain) {
        log.info("Messaging " + domain + " rePublishEvent 스케줄러 시작");
        try {
            runnable.run();
        } catch (Exception e) {
            throw new BatchBusinessException(e);
        }
        log.info("Messaging " + domain + " rePublishEvent 스케줄러 완료");
    }
}
