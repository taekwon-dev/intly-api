package com.youn.intly.unit.post.like;

import com.youn.intly.post.domain.Post;
import com.youn.intly.post.domain.content.PostContent;
import com.youn.intly.post.domain.content.PostTitle;
import com.youn.intly.post.domain.like.PostLike;
import com.youn.intly.user.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PostLikeTest {

    @Test
    @DisplayName("PostLike의 포스트, 유저 기준으로, Objects.equals()를 판단한다.")
    void isLikedBy() {
        Post post = Post.builder()
                .title(new PostTitle("title"))
                .content(new PostContent("content"))
                .build();

        User user = new User(1L, "user");
        PostLike like = new PostLike(post, user);

        assertThat(like.equals(new PostLike(post, user)));
    }
}