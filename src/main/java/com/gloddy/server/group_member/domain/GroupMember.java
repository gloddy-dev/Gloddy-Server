package com.gloddy.server.group_member.domain;

import com.gloddy.server.article.domain.Article;
import com.gloddy.server.auth.domain.User;
import com.gloddy.server.core.event.group_member.GroupMemberSelectBestMateEvent;
import com.gloddy.server.core.event.reliability.ReliabilityScoreUpdateEvent;
import com.gloddy.server.group_member.domain.dto.GroupMemberRequest;
import com.gloddy.server.group_member.domain.service.GroupMemberPraisePolicy;
import com.gloddy.server.group_member.domain.service.GroupMemberPraiser;
import com.gloddy.server.group.domain.Group;
import com.gloddy.server.group.domain.vo.GroupMemberVO;
import com.gloddy.server.reliability.domain.vo.ScoreType;
import lombok.*;
import org.springframework.context.ApplicationEventPublisher;

import javax.persistence.*;

import java.util.List;

import static com.gloddy.server.group_member.domain.dto.GroupMemberRequest.Estimate.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Getter
@Table(name = "group_member")
@EqualsAndHashCode(of = {"id"})
public class GroupMember {

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

    @Column(name = "is_praised")
    private boolean isPraised;

    public static GroupMember empty() {
        return new GroupMember();
    }

    public void init(User user, Group group) {
        this.user = user;
        this.group = group;
        this.isEnd = false;
        this.isPraised = false;
        this.absenceVoteCount = 0;
        this.isAbsence = false;
    }

    public boolean isAbsenceVoteCountOver() {
        return this.absenceVoteCount > (this.group.getMemberCount() / 2);
    }

    public void estimateGroupMembers(GroupMemberRequest.Estimate estimateInfo, GroupMemberPraisePolicy groupMemberPraisePolicy,
                                     GroupMemberPraiser groupMemberPraiser, ApplicationEventPublisher eventPublisher) {
        praiseGroupMembers(estimateInfo.getPraiseInfos(), groupMemberPraisePolicy, groupMemberPraiser);
        selectBestMate(estimateInfo.getMateInfo(), eventPublisher);
        eventPublisher.publishEvent(new ReliabilityScoreUpdateEvent(this.getUser().getId(), ScoreType.Estimated));
    }

    private void praiseGroupMembers(List<PraiseInfo> praiseInfos, GroupMemberPraisePolicy groupMemberPraisePolicy,
                                   GroupMemberPraiser groupMemberPraiser) {
        groupMemberPraisePolicy.validate(this.getGroup().getId(), this.getUser().getId(), praiseInfos);
        groupMemberPraiser.praise(this.getGroup().getId(), praiseInfos);
    }

    private void selectBestMate(MateInfo mateInfo, ApplicationEventPublisher eventPublisher) {
        eventPublisher.publishEvent(new GroupMemberSelectBestMateEvent(mateInfo, this.user.getId()));
    }

    public void completePraise() {
        this.isPraised = true;
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
        return this.user == this.getGroup().getCaptain();
    }
}
