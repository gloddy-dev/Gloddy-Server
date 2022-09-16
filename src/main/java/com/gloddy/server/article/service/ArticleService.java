package com.gloddy.server.article.service;

import com.gloddy.server.article.dto.ArticleRequest;
import com.gloddy.server.article.dto.ArticleResponse;
import com.gloddy.server.article.entity.Article;
import com.gloddy.server.article.repository.ArticleJpaRepository;
import com.gloddy.server.auth.entity.User;
import com.gloddy.server.auth.repository.UserRepository;
import com.gloddy.server.group.entity.Group;
import com.gloddy.server.group.repository.GroupJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleJpaRepository articleJpaRepository;
    private final UserRepository userRepository;
    private final GroupJpaRepository groupJpaRepository;

    public ArticleResponse.Create create(Long groupId, Long userId, ArticleRequest.Create request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("유저 없음"));
        Group group = groupJpaRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("그룹 없음"));

        Article article = Article.builder()
                .content(request.getContent())
                .isNotice(request.isNotice())
                .user(user)
                .group(group)
                .build();
        articleJpaRepository.save(article);
        return new ArticleResponse.Create(article.getId());
    }

    public ArticleResponse.Update update(Long articleId, Long userId, ArticleRequest.Update request) {
        Article article = articleJpaRepository.findById(articleId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 게시글입니다."));
        if(!article.getUser().getId().equals(userId)) {
            throw new RuntimeException("게시글 작성자가 아닙니다.");
        }
        article.update(request.getContent(), request.isNotice());
        Article updateArticle = articleJpaRepository.save(article);
        return new ArticleResponse.Update(
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


}
