package com.gloddy.server.batch.group;

import com.gloddy.server.batch.group.event.GroupApproachingEvent;
import com.gloddy.server.batch.group.event.GroupEndEvent;
import com.gloddy.server.batch.group.event.producer.GroupBatchEventProducer;
import com.gloddy.server.core.error.handler.exception.BatchBusinessException;
import com.gloddy.server.batch.group.repository.IGroupRepository;
import com.gloddy.server.group.domain.Group;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@EnableScheduling
@Component
@Slf4j
@RequiredArgsConstructor
public class GroupScheduler {

    private final GroupBatchEventProducer groupBatchEventProducer;
    private final IGroupRepository groupRepository;

    @Scheduled(cron = "0 * * * * *", zone = "Asia/Seoul")
    public void publishGroupApproachingEvent() {
        LocalDateTime nowDateTime = getNowDateTime();
        List<Group> approachingGroups = groupRepository.findApproachingGroups(nowDateTime);

        executeJob(() -> {
            approachingGroups.stream()
                 .map(group -> new GroupApproachingEvent(group.getId()))
                 .forEach(groupBatchEventProducer::produceEvent);
        });
    }

    @Scheduled(cron = "0 * * * * *", zone = "Asia/Seoul")
    public void publishGroupEndEvent() {
        LocalDateTime nowDateTime = getNowDateTime();
        List<Group> endGroups = groupRepository.findEndGroups(nowDateTime);

        executeJob(() -> {
            endGroups.stream()
                    .map(group -> new GroupEndEvent(group.getId()))
                    .forEach(groupBatchEventProducer::produceEvent);
        });
    }

    private void executeJob(Runnable runnable) {
        log.info("publishGroupEndEvent 스케줄러 시작");
        try {
            runnable.run();
        } catch (Exception e) {
            throw new BatchBusinessException(e);
        }
        log.info("publishGroupEndEvent 스케줄러 완료");
    }

    private LocalDateTime getNowDateTime() {
        LocalDate nowDate = LocalDate.now();
        LocalTime nowTime = LocalTime.of(LocalTime.now().getHour(), LocalTime.now().getMinute());

        return LocalDateTime.of(nowDate, nowTime);
    }
}
