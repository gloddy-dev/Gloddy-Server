package com.gloddy.server.comment.infra.repository.impl;

import com.gloddy.server.article.domain.Article;
import com.gloddy.server.comment.domain.Comment;
import com.gloddy.server.comment.infra.repository.custom.CommentJpaRepositoryCustom;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.gloddy.server.auth.domain.QUser.*;
import static com.gloddy.server.comment.domain.QComment.*;

@Repository
@RequiredArgsConstructor
public class CommentJpaRepositoryImpl implements CommentJpaRepositoryCustom {

    private final JPAQueryFactory query;

    @Override
    public List<Comment> findAllByArticleFetchUser(Article article) {
        return query.selectFrom(comment)
                .join(comment.user, user).fetchJoin()
                .where(eqArticle(article))
                .orderBy(comment.createdAt.desc())
                .fetch();
    }

    private BooleanExpression eqArticle(Article article) {
        return comment.article.eq(article);
    }
}
