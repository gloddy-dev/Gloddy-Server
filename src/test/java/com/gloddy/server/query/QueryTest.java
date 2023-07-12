package com.gloddy.server.query;

import com.gloddy.server.article.infra.repository.ArticleJpaRepository;
import com.gloddy.server.comment.infra.repository.CommentJpaRepository;
import com.gloddy.server.praise.infra.repository.PraiseJpaRepository;
import com.gloddy.server.group.infra.repository.GroupJpaRepository;
import com.gloddy.server.group_member.infra.repository.GroupMemberJpaRepository;
import com.gloddy.server.reliability.infra.repository.ReliabilityRepository;
import com.gloddy.server.user.infra.repository.UserJpaRepository;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;


@DataJpaTest
@Import(TestConfig.class)
public abstract class QueryTest {

    @Autowired
    protected GroupJpaRepository groupJpaRepository;

    @Autowired
    protected GroupMemberJpaRepository userGroupJpaRepository;

    @Autowired
    protected UserJpaRepository userJpaRepository;

    @Autowired
    protected PraiseJpaRepository praiseJpaRepository;

    @Autowired
    protected ReliabilityRepository reliabilityRepository;

    @Autowired
    protected ArticleJpaRepository articleJpaRepository;

    @Autowired
    protected CommentJpaRepository commentJpaRepository;

    @Autowired
    protected EntityManager em;
}
