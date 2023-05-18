package com.gloddy.server.article.application;

import com.gloddy.server.article.domain.dto.ArticleRequest;
import com.gloddy.server.article.domain.dto.ArticleResponse;
import com.gloddy.server.article.domain.dto.ImageDto;
import com.gloddy.server.article.domain.Article;
import com.gloddy.server.article.domain.handler.ArticleCommandHandler;
import com.gloddy.server.article.domain.handler.ArticleQueryHandler;
import com.gloddy.server.article.domain.service.ArticleDeletePolicy;
import com.gloddy.server.article.domain.service.ArticleUpdatePolicy;
import com.gloddy.server.article.domain.service.NoticeArticleCreatePolicy;
import com.gloddy.server.article.infra.repository.ArticleJpaRepository;
import com.gloddy.server.auth.domain.User;
import com.gloddy.server.group.domain.UserGroup;
import com.gloddy.server.group.domain.handler.GroupQueryHandler;
import com.gloddy.server.group.domain.handler.UserGroupQueryHandler;
import com.gloddy.server.user.domain.handler.UserQueryHandler;
import com.gloddy.server.comment.application.CommentService;
import com.gloddy.server.core.error.handler.errorCode.ErrorCode;
import com.gloddy.server.core.error.handler.exception.ArticleBusinessException;
import com.gloddy.server.core.response.PageResponse;
import com.gloddy.server.group.domain.Group;
import com.gloddy.server.group.domain.handler.GroupHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.gloddy.server.article.domain.dto.ArticleResponse.*;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleJpaRepository articleJpaRepository;
    private final ArticleQueryHandler articleQueryHandler;
    private final ArticleCommandHandler articleCommandHandler;
    private final UserQueryHandler userQueryHandler;
    private final GroupQueryHandler groupQueryHandler;
    private final UserGroupQueryHandler userGroupQueryHandler;
    private final ImageService imageService;
    private final CommentService commentService;
    private final NoticeArticleCreatePolicy noticeArticleCreatePolicy;
    private final ArticleUpdatePolicy articleUpdatePolicy;
    private final ArticleDeletePolicy articleDeletePolicy;

    @Transactional
    public ArticleResponse.Create create(Long groupId, Long userId, ArticleRequest.Create request) {
        UserGroup userGroup = userGroupQueryHandler.findByUserIdAndGroupId(userId, groupId);

        if (request.isNotice()) {
            noticeArticleCreatePolicy.validate(userGroup);
        }

        Article article = userGroup.createArticle(request.getContent(), request.isNotice());

        articleCommandHandler.save(article);
        if (!request.getImages().isEmpty()) {
            article.createAndAddAllArticleImages(request.getImages());
        }

        return new ArticleResponse.Create(article.getId());
    }

    @Transactional
    public void update(Long articleId, Long userId, ArticleRequest.Update request) {
        Article article = articleQueryHandler.findById(articleId);
        User user = userQueryHandler.findById(userId);

        articleUpdatePolicy.validate(article, user);

        article.update(request.getContent(), request.isNotice());
        article.upsertArticleImages(request.getImages());
    }

    @Transactional
    public void delete(Long groupId, Long articleId, Long userId) {
        UserGroup userGroup = userGroupQueryHandler.findByUserIdAndGroupId(userId, groupId);
        Article article = articleQueryHandler.findById(articleId);

        articleDeletePolicy.validate(article, userGroup);

        articleCommandHandler.delete(article);
    }

    @Transactional(readOnly = true)
    public GetPreview getPreview(Long groupId, int page, int size) {
        Group group = groupQueryHandler.findById(groupId);
        Pageable pageable = PageRequest.of(page, size);
        Page<GetArticle> articles = articleJpaRepository.findAllByGroup(group, pageable)
                .map(this::getArticle);

        return new GetPreview(
            group.getFileUrl(),
            group.getTitle(),
            group.getContent(),
            PageResponse.from(articles)
        );
    }

    private GetArticle getArticle(Article article) {
        User writer = article.getUser();
        int commentCount = commentService.getCommentCount(article);
        List<ImageDto> images = imageService.get(article);

        return new GetArticle(
                writer.getImageUrl(),
                writer.getName(),
                formatDate(article.getCreatedAt()),
                article.getContent(),
                article.isNotice(),
                commentCount,
                images
        );
    }

    private String formatDate(LocalDateTime date) {
        return date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }
}
