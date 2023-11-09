package com.gloddy.server.article.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class GroupArticleCreateEvent {
    private Long userId;
    private Long groupId;
    private Long articleId;
    private boolean isNotice;
}
