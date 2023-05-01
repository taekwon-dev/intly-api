package com.youn.intly.unit.post.like;

import com.youn.intly.post.domain.Post;
import com.youn.intly.post.domain.content.PostContent;
import com.youn.intly.post.domain.content.PostTitle;
import com.youn.intly.post.domain.like.PostLike;
import com.youn.intly.post.domain.like.PostLikes;
import com.youn.intly.user.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class PostLikesTest {

    private Post post;
    private PostLikes likes;

    @BeforeEach
    void setUp() {
        post = Post.builder()
                .title(new PostTitle("title"))
                .content(new PostContent("content"))
                .build();

        User user1 = new User(1L, "user1");
        User user2 = new User(2L, "user2");
        User user3 = new User(3L, "user3");

        likes = new PostLikes(List.of(
                new PostLike(post, user1),
                new PostLike(post, user2),
                new PostLike(post, user3)
        ));
    }

    @Test
    @DisplayName("포스트 좋아요 개수를 확인한다.")
    void getCount() {
        assertThat(likes.getCount()).isEqualTo(3);
    }
    
    @ParameterizedTest
    @CsvSource(value = {"1, user1, true", "2, user2, true", "4, none, false"})
    @DisplayName("특정 포스트의 좋아요한 유저에 특정 사용자가 포함되어 있는지 확인한다.")
    void contains(Long userId, String username, boolean expected) {
        User user = new User(userId, username);

        assertThat(likes.contains(new PostLike(post, user))).isEqualTo(expected);
    }
}