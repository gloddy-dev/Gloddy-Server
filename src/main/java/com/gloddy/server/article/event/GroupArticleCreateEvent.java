package com.gloddy.server.article.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class GroupArticleCreateEvent {
    private Long userId;
    private Long groupId;
    private List<Long> groupMemberUserIds;
    private Long articleId;
    private boolean isNotice;
}
