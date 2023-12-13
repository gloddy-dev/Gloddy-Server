package com.gloddy.server.group_member.domain;

import com.gloddy.server.article.domain.Article;
import com.gloddy.server.user.domain.User;
import com.gloddy.server.core.entity.common.BaseTimeEntity;
import com.gloddy.server.group_member.event.GroupMemberEstimateCompleteEvent;
import com.gloddy.server.group_member.event.GroupMemberSelectBestMateEvent;
import com.gloddy.server.group_member.domain.dto.GroupMemberRequest;
import com.gloddy.server.group_member.domain.service.GroupMemberPraisePolicy;
import com.gloddy.server.group_member.domain.service.GroupMemberPraiser;
import com.gloddy.server.group.domain.Group;
import com.gloddy.server.group.domain.vo.GroupMemberVO;
import com.gloddy.server.group_member.event.producer.GroupMemberEventProducer;
import lombok.*;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

import static com.gloddy.server.group_member.domain.dto.GroupMemberRequest.Estimate.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Getter
@Table(name = "group_member")
@EqualsAndHashCode(of = {"id"}, callSuper = false)
public class GroupMember extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private Group group;

    @Column(name = "absence_vote_count", nullable = false)
    private Integer absenceVoteCount;

    @Column(name = "is_absence", nullable = false)
    private Boolean isAbsence;

    //나중에 스케줄링으로 처리하면 너무 편할 듯
    @Column(name = "is_end")
    private boolean isEnd;

    @Column(name = "is_end_estimate")
    private boolean isEndEstimate;

    public static GroupMember empty() {
        return new GroupMember();
    }

    public void init(User user, Group group) {
        this.user = user;
        this.group = group;
        this.isEnd = false;
        this.isEndEstimate = false;
        this.absenceVoteCount = 0;
        this.isAbsence = false;
    }

    public boolean isAbsenceVoteCountOver() {
        return this.absenceVoteCount > (this.group.getMemberCount() / 2);
    }

    public void estimateGroupMembers(GroupMemberRequest.Estimate estimateInfo, GroupMemberPraisePolicy groupMemberPraisePolicy,
                                     GroupMemberPraiser groupMemberPraiser, GroupMemberEventProducer groupMemberEventProducer) {
        praiseGroupMembers(estimateInfo.getPraiseInfos(), groupMemberPraisePolicy, groupMemberPraiser);
        selectBestMate(estimateInfo.getMateInfo(), groupMemberEventProducer);
        completeEstimate();
        groupMemberEventProducer.produceEvent(new GroupMemberEstimateCompleteEvent(this.getUser().getId()));
    }

    private void praiseGroupMembers(List<PraiseInfo> praiseInfos, GroupMemberPraisePolicy groupMemberPraisePolicy,
                                   GroupMemberPraiser groupMemberPraiser) {
        groupMemberPraisePolicy.validate(this.getGroup().getId(), this.getUser().getId(), praiseInfos);
        groupMemberPraiser.praise(this.getGroup().getId(), praiseInfos);
    }

    private void selectBestMate(MateInfo mateInfo, GroupMemberEventProducer groupMemberEventProducer) {
        groupMemberEventProducer.produceEvent(new GroupMemberSelectBestMateEvent(mateInfo, this.user.getId()));
    }

    public void completeEstimate() {
        this.isEndEstimate = true;
    }

    public void plusAbsenceVoteCount() {
        this.absenceVoteCount++;
    }

    public void absence() {
        this.isAbsence = true;
    }

    public boolean isAbsence() {
        return this.isAbsence;
    }

    public Article createArticle(String content, boolean isNotice) {
        return Article.builder()
                .content(content)
                .notice(isNotice)
                .user(this.getUser())
                .group(this.getGroup())
                .build();
    }

    public GroupMemberVO createUserGroupVO() {
        return GroupMemberVO.builder()
                .userId(this.user.getId())
                .build();
    }

    public boolean isCaptain() {
        return this.user.equals(this.getGroup().getCaptain());
    }

    public boolean isNewGroupMember() {
        return this.getCreatedAt().isAfter(LocalDateTime.now().minusHours(1));
    }
}
