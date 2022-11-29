package com.gloddy.server.article.service;

import com.gloddy.server.article.dto.ArticleRequest;
import com.gloddy.server.article.dto.ArticleResponse;
import com.gloddy.server.article.entity.Article;
import com.gloddy.server.article.repository.ArticleJpaRepository;
import com.gloddy.server.auth.entity.User;
import com.gloddy.server.auth.handler.UserHandler;
import com.gloddy.server.auth.repository.UserRepository;
import com.gloddy.server.comment.repository.CommentJpaRepository;
import com.gloddy.server.group.entity.Group;
import com.gloddy.server.group.handler.GroupHandler;
import com.gloddy.server.group.repository.GroupJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.gloddy.server.article.dto.ArticleResponse.*;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleJpaRepository articleJpaRepository;
    private final CommentJpaRepository commentJpaRepository;
    private final UserHandler userHandler;
    private final GroupHandler groupHandler;

    public Create create(Long groupId, Long userId, ArticleRequest.Create request) {
        User user = userHandler.findById(userId);
        Group group = groupHandler.findById(groupId);

        Article article = Article.builder()
                .content(request.getContent())
                .isNotice(request.isNotice())
                .user(user)
                .group(group)
                .build();
        articleJpaRepository.save(article);
        return new Create(article.getId());
    }

    public Update update(Long articleId, Long userId, ArticleRequest.Update request) {
        Article article = articleJpaRepository.findById(articleId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 게시글입니다."));
        if(!article.getUser().getId().equals(userId)) {
            throw new RuntimeException("게시글 작성자가 아닙니다.");
        }
        article.update(request.getContent(), request.isNotice());
        Article updateArticle = articleJpaRepository.save(article);
        return new Update(
                updateArticle.getContent(),
                updateArticle.isNotice()
        );
    }

    // TODO: 그룹 captain도 해당 그룹 게시글 삭제할 수 있도록 수정 필요
    public void delete(Long articleId, Long userId) {
        Article article = articleJpaRepository.findById(articleId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 게시글입니다."));
        if(!article.getUser().getId().equals(userId)) {
            throw new RuntimeException("게시글 작성자가 아닙니다.");
        }
        articleJpaRepository.delete(article);
    }

    // TODO: 추후 좋아요 여부 구현 위해 userId 받음
    // TODO: 그룹 이미지 추가해야됨
    public GetPreview getPreview(Long groupId, Long userId) {
        Group group = groupHandler.findById(groupId);
        List<Article> articles = articleJpaRepository.findAllByGroup(group);

        List<Preview> previews = articles.stream()
            .map(this::getPreview)
            .collect(Collectors.toList());

        return new GetPreview(
//            group.getImageUrl();
            group.getTitle(),
            group.getContent(),
            previews
        );
    }

    // TODO: 게시글 작성자 이미지 추가 해야됨.
    private Preview getPreview(Article article) {
        User writer = article.getUser();
        int commentCount = commentJpaRepository.countAllByArticle(article);
        String date = article.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        return new Preview(
//            writer.getImageUrl(),
            writer.getName(),
            date,
            article.getContent(),
            commentCount
        );
    }
}
