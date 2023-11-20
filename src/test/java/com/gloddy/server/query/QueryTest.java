package com.gloddy.server.query;

import com.gloddy.server.article.infra.repository.ArticleJpaRepository;
import com.gloddy.server.auth.infra.repository.impl.IVerifyCodeRepository;
import com.gloddy.server.comment.infra.repository.CommentJpaRepository;
import com.gloddy.server.group.infra.repository.GroupJpaRepository;
import com.gloddy.server.group_member.infra.repository.GroupMemberJpaRepository;
import com.gloddy.server.user.infra.repository.UserJpaRepository;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;


@DataJpaTest
@Import({TestConfig.class, IVerifyCodeRepository.class})
public abstract class QueryTest {

    @Autowired
    protected GroupJpaRepository groupJpaRepository;

    @Autowired
    protected GroupMemberJpaRepository userGroupJpaRepository;

    @Autowired
    protected UserJpaRepository userJpaRepository;

    @Autowired
    protected ArticleJpaRepository articleJpaRepository;

    @Autowired
    protected CommentJpaRepository commentJpaRepository;

    @Autowired
    protected EntityManager em;
}
