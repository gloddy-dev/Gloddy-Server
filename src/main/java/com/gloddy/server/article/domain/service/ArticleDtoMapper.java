package com.gloddy.server.article.domain.service;

import com.gloddy.server.article.domain.Article;
import com.gloddy.server.article.domain.dto.ArticleResponse;
import com.gloddy.server.article.domain.dto.ImageDto;
import com.gloddy.server.auth.domain.User;
import com.gloddy.server.auth.domain.vo.Profile;
import com.gloddy.server.core.utils.DateTimeUtils;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;


public class ArticleDtoMapper {

    public static Page<ArticleResponse.GetArticle> mapToGetArticlePageFrom(Page<Article> articles) {
        return articles.map(ArticleDtoMapper::getArticle);
    }

    private static ArticleResponse.GetArticle getArticle(Article article) {
        User user = article.getUser();
        Profile profile = user.getProfile();

        return new ArticleResponse.GetArticle(
                profile.getImageUrl(),
                profile.getNickname(),
                DateTimeUtils.dateTimeToString(article.getCreatedAt()),
                article.getContent(),
                article.isNotice(),
                article.getCommentCount(),
                getImageDtos(article)
        );
    }

    private static List<ImageDto> getImageDtos(Article article) {
        return article.getImages()
                .stream()
                .map(image -> new ImageDto(image.getUrl()))
                .collect(Collectors.toUnmodifiableList());
    }

}
