package com.gloddy.server.query.article;

import com.gloddy.server.article.domain.Article;
import com.gloddy.server.auth.domain.User;
import com.gloddy.server.auth.domain.vo.Profile;
import com.gloddy.server.auth.domain.vo.kind.Personality;
import com.gloddy.server.comment.domain.Comment;
import com.gloddy.server.group.domain.Group;
import com.gloddy.server.query.QueryTest;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class FindAllByGroupFetchUserAndGroupAndCommendsTest extends QueryTest {

    private User getUser() {
        Profile profile = Profile.builder()
                .personalities(List.of(Personality.KIND))
                .build();
        User user = User.builder().profile(profile).build();
        return userJpaRepository.save(user);
    }

    private Group getGroup() {
        Profile profile = Profile.builder()
                .personalities(List.of(Personality.KIND))
                .build();
        User user = User.builder().profile(profile).build();
        userJpaRepository.save(user);

        Group group = Group.builder().captain(user).build();
        return groupJpaRepository.save(group);
    }

    private Article getArticle(User user, Group group) {
        Article article = Article.builder().user(user).group(group).build();
        return articleJpaRepository.save(article);
    }

    private List<Comment> saveComments(Article article) {
        ArrayList<Comment> comments = new ArrayList<>();
        User user = getUser();

        for (int i = 0; i < 10; i++) {
            comments.add(Comment.builder().user(user).article(article).build());
        }

        return commentJpaRepository.saveAll(comments);
    }

    @Test
    public void test() {
        User writer = getUser();
        Group group = getGroup();
        Article article = getArticle(writer, group);

        List<Comment> comments = saveComments(article);

        em.flush();
        em.clear();

        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<Article> articles = articleJpaRepository.findAllByGroupFetchUserAndGroup(group, pageRequest);

        assertThat(articles.getTotalElements()).isEqualTo(1);
        assertThat(articles.getContent().get(0).getId()).isEqualTo(article.getId());
        assertThat(articles.getContent().get(0).getComments().size()).isEqualTo(comments.size());
    }
}
