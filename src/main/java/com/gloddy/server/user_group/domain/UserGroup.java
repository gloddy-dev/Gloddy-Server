package com.gloddy.server.user_group.domain;

import com.gloddy.server.article.domain.Article;
import com.gloddy.server.auth.domain.User;
import com.gloddy.server.praise.domain.vo.PraiseValue;
import com.gloddy.server.praise.domain.service.PraiseStrategy;
import com.gloddy.server.praise.domain.service.PraiseStrategyFactory;
import com.gloddy.server.group.domain.Group;
import com.gloddy.server.group.domain.vo.UserGroupVO;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Getter
@Table(name = "user_group")
public class UserGroup {

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

    public static UserGroup empty() {
        return new UserGroup();
    }

    public void init(User user, Group group) {
        this.user = user;
        this.group = group;
        this.isEnd = false;
        this.isPraised = false;
        this.absenceVoteCount = 0;
        this.isAbsence = false;
    }

    public void receivePraise(PraiseValue praiseValue) {
        PraiseStrategy praiseStrategy = PraiseStrategyFactory.getStrategy(praiseValue);
        praiseStrategy.praise(this);
    }

    public boolean isAbsenceVoteCountOver() {
        return this.absenceVoteCount > (this.group.getMemberCount() / 2);
    }

    public boolean isAlreadyAbsenceVoteCountOver() {
        return (this.absenceVoteCount - 1) > (this.group.getMemberCount() / 2);
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

    public UserGroupVO createUserGroupVO() {
        return UserGroupVO.builder()
                .userId(this.user.getId())
                .build();
    }

    public boolean isCaptain() {
        return this.user == this.getGroup().getCaptain();
    }
}
