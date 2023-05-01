package com.youn.intly.unit.post;

import com.youn.intly.exception.post.CannotUnlikeException;
import com.youn.intly.exception.post.DuplicatedLikeException;
import com.youn.intly.exception.tag.CannotAddTagException;
import com.youn.intly.post.domain.Post;
import com.youn.intly.post.domain.content.PostContent;
import com.youn.intly.post.domain.content.PostTitle;
import com.youn.intly.tag.domain.Tag;
import com.youn.intly.user.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class PostTest {

    @Test
    @DisplayName("포스트 생성 시 태그를 등록할 수 있다.")
    void addTagsOnPost() {
        Post post = Post.builder()
                .title(new PostTitle("title"))
                .content(new PostContent("content"))
                .build();

        List<Tag> tags = List.of(
            new Tag("tag1"),
            new Tag("tag2"),
            new Tag("tag3")
        );

        post.addTags(tags);

        assertThat(post.getTagNames()).hasSize(3);
    }

    @Test
    @DisplayName("포스트 생성 시 중복된 태그가 있는 경우 태그를 등록할 수 없다.")
    void addDuplicatedTagsOnPost_ExceptionThrown() {
        Post post = Post.builder()
                .title(new PostTitle("title"))
                .content(new PostContent("content"))
                .build();

        List<Tag> duplicatedTags = List.of(
            new Tag("tag1"),
            new Tag("tag2"),
            new Tag("tag2")
        );

        assertThatThrownBy(() -> post.addTags(duplicatedTags))
                .isInstanceOf(CannotAddTagException.class)
                .hasFieldOrPropertyWithValue("httpStatus", HttpStatus.BAD_REQUEST);
    }

    @Test
    @DisplayName("유저는 포스트에 좋아요 할 수 있다.")
    void likeOnPost() {
        Post post = Post.builder()
                .title(new PostTitle("title"))
                .content(new PostContent("content"))
                .build();

        User user = new User(1L, "user");

        post.like(user);

        assertThat(post.getLikeCount()).isEqualTo(1);
        assertThat(post.isLikedBy(user)).isTrue();
    }

    @Test
    @DisplayName("유저는 이미 좋아요 누른 포스트에 좋아요 할 수 없다.")
    void likeOnAlreadyLikePost_ExceptionThrown() {
        Post post = Post.builder()
                .title(new PostTitle("title"))
                .content(new PostContent("content"))
                .build();

        User user = new User(1L, "user");

        post.like(user);

        assertThatThrownBy(() -> post.like(user))
                .isInstanceOf(DuplicatedLikeException.class)
                .hasFieldOrPropertyWithValue("httpStatus", HttpStatus.BAD_REQUEST);
    }

    @Test
    @DisplayName("유저는 포스트에 남긴 좋아요를 취소할 수 있다.")
    void cancelLikeOnPost() {
        Post post = Post.builder()
                .title(new PostTitle("title"))
                .content(new PostContent("content"))
                .build();

        User user = new User(1L, "user");

        post.like(user);
        assertThat(post.getLikeCount()).isEqualTo(1);
        assertThat(post.isLikedBy(user)).isTrue();

        post.cancelLike(user);
        assertThat(post.getLikeCount()).isEqualTo(0);
        assertThat(post.isLikedBy(user)).isFalse();
    }

    @Test
    @DisplayName("유저는 좋아요 하지 않은 포스트를 좋아요 취소할 수 없다.")
    void cancelLikeOnNonLikePost_ExceptionThrown() {
        Post post = Post.builder()
                .title(new PostTitle("title"))
                .content(new PostContent("content"))
                .build();

        User user = new User(1L, "user");

        assertThatThrownBy(() -> post.cancelLike(user))
                .isInstanceOf(CannotUnlikeException.class)
                .hasFieldOrPropertyWithValue("httpStatus", HttpStatus.BAD_REQUEST);
    }
}