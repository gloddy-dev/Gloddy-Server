package com.gloddy.server.article.domain.dto;

import com.gloddy.server.article.domain.Article;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ArticlePayload {
    private Long groupId;
    private Long captainId;
    private Long writerId;
    private String groupImage;
    private List<Long> groupMemberIds;

    public static ArticlePayload toDto(Article article) {
        return new ArticlePayload(
                article.getGroup().getId(),
                article.getGroup().getCaptainId(),
                article.getUser().getId(),
                article.getGroup().getImageUrl(),
                article.getGroup().getGroupMemberUserIds()
        );
    }
}
