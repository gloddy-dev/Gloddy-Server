package com.gloddy.server.article.domain.service;

import com.gloddy.server.article.domain.Article;
import com.gloddy.server.article.domain.vo.ArticleImage;
import com.gloddy.server.auth.domain.User;
import com.gloddy.server.auth.domain.vo.Profile;
import com.gloddy.server.core.utils.DateTimePatternConstants;
import com.gloddy.server.core.utils.DateTimeUtils;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

import static com.gloddy.server.article.domain.dto.ArticleResponse.*;


public class ArticleDtoMapper {

    public static Page<GetArticle> mapToGetArticlePageFrom(Page<Article> articles, User currentUser) {
        return articles.map(article -> getArticleDto(article,  currentUser));
    }

    public static GetArticle getArticleDto(Article article, User currentUser) {
        User user = article.getUser();
        Profile profile = user.getProfile();

        return new GetArticle(
                user.getId(),
                article.getId(),
                profile.getImageUrl(),
                profile.getNickname(),
                toStringDateTime(article),
                article.getContent(),
                article.isNotice(),
                article.getCommentCount(),
                article.isWriter(currentUser),
                article.isWriterGroupCaptain(),
                article.isWriterCertifiedStudent(),
                article.getWriterReliabilityLevel().name(),
                getImageDtos(article)
        );
    }

    private static List<String> getImageDtos(Article article) {
        return article.getImages()
                .stream()
                .map(ArticleImage::getUrl)
                .collect(Collectors.toUnmodifiableList());
    }

    public static List<GetNotice> getNoticeDto(List<Article> articles) {
        return articles.stream()
                .map(ArticleDtoMapper::toNoticeDto)
                .toList();
    }

    private static GetNotice toNoticeDto(Article article) {
        return new GetNotice(
                article.getId(),
                article.getContent()
        );
    }

    private static String toStringDateTime(Article article) {
        return DateTimeUtils.dateTimeToString(
                article.getCreatedAt(),
                DateTimePatternConstants.ARTICLE
        );
    }
}
