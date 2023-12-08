package com.gloddy.server.batch.group;

import static com.gloddy.server.core.error.handler.errorCode.ErrorCode.GROUP_APPROACHING_NOTIFICATION_BATCH_ERROR;
import static com.gloddy.server.core.error.handler.errorCode.ErrorCode.GROUP_START_NOTIFICATION_BATCH_ERROR;
import static com.gloddy.server.messaging.adapter.group.event.GroupEventType.APPROACHING_GROUP;
import static com.gloddy.server.messaging.adapter.group.event.GroupEventType.END_GROUP;

import com.gloddy.server.core.error.handler.errorCode.ErrorCode;
import com.gloddy.server.core.error.handler.exception.BatchBusinessException;
import com.gloddy.server.messaging.adapter.group.event.GroupAdapterEvent;
import com.gloddy.server.messaging.adapter.group.event.GroupEventType;
import com.gloddy.server.batch.group.repository.IGroupRepository;
import com.gloddy.server.group.domain.Group;
import com.gloddy.server.messaging.MessagePublisher;
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

    private final MessagePublisher messagePublisher;
    private final IGroupRepository groupRepository;

    @Scheduled(cron = "0 * * * * *", zone = "Asia/Seoul")
    public void publishGroupApproachingEvent() {
        LocalDateTime nowDateTime = getNowDateTime();
        List<Group> approachingGroups = groupRepository.findApproachingGroups(nowDateTime);

        executeJob(approachingGroups, APPROACHING_GROUP, GROUP_APPROACHING_NOTIFICATION_BATCH_ERROR);
    }

    @Scheduled(cron = "0 * * * * *", zone = "Asia/Seoul")
    public void publishGroupEndEvent() {
        LocalDateTime nowDateTime = getNowDateTime();
        List<Group> endGroups = groupRepository.findEndGroups(nowDateTime);

        executeJob(endGroups, END_GROUP, GROUP_START_NOTIFICATION_BATCH_ERROR);
    }

    private void executeJob(List<Group> groups, GroupEventType eventType, ErrorCode errorCode) {
        log.info("publishGroupEndEvent 스케줄러 시작");
        try {
            groups.stream()
                    .map(group -> new GroupAdapterEvent(group.getId(), eventType, LocalDateTime.now()))
                    .forEach(messagePublisher::publishGroupStatusEvent);
        } catch (Exception e) {
            throw new BatchBusinessException(errorCode, e);
        }
        log.info("publishGroupEndEvent 스케줄러 완료");
    }

    private LocalDateTime getNowDateTime() {
        LocalDate nowDate = LocalDate.now();
        LocalTime nowTime = LocalTime.of(LocalTime.now().getHour(), LocalTime.now().getMinute());

        return LocalDateTime.of(nowDate, nowTime);
    }
}
