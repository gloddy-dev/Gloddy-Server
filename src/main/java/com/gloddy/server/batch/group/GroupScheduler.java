package com.gloddy.server.batch.group;

import com.gloddy.server.messaging.adapter.group.event.GroupEvent;
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
        log.info("publishGroupApproachingEvent 스케줄러 시작");
        try {
            LocalDateTime nowDateTime = getNowDateTime();
            List<Group> approachingGroups = groupRepository.findApproachingGroups(nowDateTime);

            approachingGroups.stream()
                    .map(group -> new GroupEvent(group.getId(), group.getGroupMemberUserIds(), GroupEventType.APPROACHING_GROUP))
                    .forEach(event -> messagePublisher.publishGroupStatusEvent(event));
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException("publishGroupApproachingEvent 스케줄러 실패");
        }
        log.info("publishGroupApproachingEvent 스케줄러 완료");
    }

    @Scheduled(cron = "0 * * * * *", zone = "Asia/Seoul")
    public void publishGroupEndEvent() {
        log.info("publishGroupEndEvent 스케줄러 시작");
        try {
            LocalDateTime nowDateTime = getNowDateTime();
            List<Group> endGroups = groupRepository.findEndGroups(nowDateTime);

            endGroups.stream()
                    .map(group -> new GroupEvent(group.getId(), group.getGroupMemberUserIds(), GroupEventType.END_GROUP))
                    .forEach(event -> messagePublisher.publishGroupStatusEvent(event));
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException("publishGroupEndEvent 스케줄러 실패");
        }
        log.info("publishGroupEndEvent 스케줄러 완료");
    }

    private LocalDateTime getNowDateTime() {
        LocalDate nowDate = LocalDate.now();
        LocalTime nowTime = LocalTime.of(LocalTime.now().getHour(), LocalTime.now().getMinute());

        return LocalDateTime.of(nowDate, nowTime);
    }
}
