package com.gloddy.server.article.application;

import com.gloddy.server.article.domain.dto.ArticleRequest;
import com.gloddy.server.article.domain.dto.ArticleResponse;
import com.gloddy.server.article.domain.Article;
import com.gloddy.server.article.domain.handler.ArticleCommandHandler;
import com.gloddy.server.article.domain.handler.ArticleQueryHandler;
import com.gloddy.server.article.domain.service.ArticleDeletePolicy;
import com.gloddy.server.article.domain.service.ArticleDtoMapper;
import com.gloddy.server.article.domain.service.ArticleUpdatePolicy;
import com.gloddy.server.article.domain.service.NoticeArticleCreatePolicy;
import com.gloddy.server.article.event.GroupArticleCreateEvent;
import com.gloddy.server.auth.domain.User;
import com.gloddy.server.group_member.domain.GroupMember;
import com.gloddy.server.group.domain.handler.GroupQueryHandler;
import com.gloddy.server.group_member.domain.handler.GroupMemberQueryHandler;
import com.gloddy.server.user.domain.handler.UserQueryHandler;
import com.gloddy.server.core.response.PageResponse;
import com.gloddy.server.group.domain.Group;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.gloddy.server.article.domain.dto.ArticleResponse.*;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleQueryHandler articleQueryHandler;
    private final ArticleCommandHandler articleCommandHandler;
    private final UserQueryHandler userQueryHandler;
    private final GroupQueryHandler groupQueryHandler;
    private final GroupMemberQueryHandler groupMemberQueryHandler;
    private final NoticeArticleCreatePolicy noticeArticleCreatePolicy;
    private final ArticleUpdatePolicy articleUpdatePolicy;
    private final ArticleDeletePolicy articleDeletePolicy;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public ArticleResponse.Create create(Long groupId, Long userId, ArticleRequest.Create request) {
        GroupMember groupMember = groupMemberQueryHandler.findByUserIdAndGroupId(userId, groupId);

        if (request.isNotice()) {
            noticeArticleCreatePolicy.validate(groupMember);
        }

        Article article = groupMember.createArticle(request.getContent(), request.isNotice());

        articleCommandHandler.save(article);
        if (!request.getImages().isEmpty()) {
            article.createAndAddAllArticleImages(request.getImages());
        }

        eventPublisher.publishEvent(
                new GroupArticleCreateEvent(
                        userId,
                        groupId,
                        article.getId(),
                        article.isNotice()
                )
        );

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
        GroupMember groupMember = groupMemberQueryHandler.findByUserIdAndGroupId(userId, groupId);
        Article article = articleQueryHandler.findById(articleId);

        articleDeletePolicy.validate(article, groupMember);

        articleCommandHandler.delete(article);
    }

    @Transactional(readOnly = true)
    public PageResponse<GetArticle> getPreview(Long userId, Long groupId, int page, int size) {
        Group group = groupQueryHandler.findById(groupId);
        User user = userQueryHandler.findById(userId);

        Pageable pageable = PageRequest.of(page, size);
        Page<Article> articles = articleQueryHandler.findAllToGetArticlePreview(group, pageable);

        Page<GetArticle> getArticles = ArticleDtoMapper.mapToGetArticlePageFrom(articles, user);
        return PageResponse.from(getArticles);
    }

    @Transactional(readOnly = true)
    public GetArticle getOne(Long userId, Long articleId) {
        Article article = articleQueryHandler.findById(articleId);
        User user = userQueryHandler.findById(userId);

        return ArticleDtoMapper.getArticleDto(article, user);
    }

    @Transactional(readOnly = true)
    public List<GetNotice> getNotice(Long groupId) {
        Group group = groupQueryHandler.findById(groupId);
        List<Article> noticeArticles = articleQueryHandler.findAllByGroupAndNotice(group, true);

        return ArticleDtoMapper.getNoticeDto(noticeArticles);
    }
}
