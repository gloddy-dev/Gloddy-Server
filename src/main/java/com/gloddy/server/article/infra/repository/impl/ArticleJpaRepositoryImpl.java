package com.gloddy.server.article.infra.repository.impl;

import com.gloddy.server.article.domain.Article;
import com.gloddy.server.article.infra.repository.custom.ArticleJpaRepositoryCustom;
import com.gloddy.server.group.domain.Group;
import com.gloddy.server.group.domain.QGroup;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.gloddy.server.article.domain.QArticle.*;
import static com.gloddy.server.auth.domain.QUser.*;

@Repository
@RequiredArgsConstructor
public class ArticleJpaRepositoryImpl implements ArticleJpaRepositoryCustom {
    private final JPAQueryFactory query;


    @Override
    public Page<Article> findAllByGroupFetchUserAndGroup(Group group, Pageable pageable) {
        List<Article> articles = query.selectDistinct(article)
                .from(article)
                .join(article.user, user).fetchJoin()
                .join(article.group, QGroup.group).fetchJoin()
                .where(groupEq(group))
                .where(noticeEq(false))
                .orderBy(article.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        int total = query.select(article.id)
                .from(article)
                .where(groupEq(group))
                .fetch()
                .size();
        return new PageImpl<>(articles, pageable, total);
    }

    private BooleanExpression groupEq(Group group) {
        return article.group.eq(group);
    }

    private BooleanExpression noticeEq(boolean isNotice) {
        return article.notice.eq(isNotice);
    }
}
