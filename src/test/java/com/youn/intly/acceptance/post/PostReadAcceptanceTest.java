package com.youn.intly.acceptance.post;

import com.youn.intly.acceptance.AcceptanceTest;
import com.youn.intly.authentication.infrastructure.AuthorizationExtractor;
import com.youn.intly.comment.dto.request.CommentRequest;
import com.youn.intly.comment.dto.request.SubCommentRequest;
import com.youn.intly.post.domain.category.ChildCategory;
import com.youn.intly.post.domain.category.ParentCategory;
import com.youn.intly.post.dto.request.PostRequest;
import com.youn.intly.user.domain.User;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.List;
import java.util.Objects;

import static com.youn.intly.acceptance.post.PostAcceptanceTest.savePostLike;
import static com.youn.intly.authentication.infrastructure.AuthorizationExtractor.AUTHORIZATION;
import static org.assertj.core.api.Assertions.assertThat;

public class PostReadAcceptanceTest extends AcceptanceTest {

    private static final String READ_POSTS_DEFAULT_URL = "/api/posts?category=%s";
    private static final String READ_POSTS_URL = "/api/posts?category=%s&postId=%s";

    private User author;
    private PostRequest postRequest;

    @BeforeEach
    void setUp() {
        author = User.builder()
                .username("YOUN")
                .build();

        postRequest = PostRequest.builder()
                .parentCategory(ParentCategory.FREE_TOPIC)
                .childCategory(ChildCategory.FREE_THREAD)
                .title("title")
                .content("content")
                .tags(List.of("java", "spring", "jpa", "mysql", "junit5"))
                .build();
    }

    @Test
    @DisplayName("로그인 유저는 포스트 상세 조회를 할 수 있다.")
    void readPostDetails_LoginUser() {
        // given
        token = getToken(author.getUsername());
        Long postId = Long.parseLong(savePost(postRequest).header("location").split("/")[2]);
        saveComment(postId, new CommentRequest("content"));
        savePostLike(postId);

        // when
        ExtractableResponse<Response> response = readPostDetails(postId);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("로그인 유저는 포스트 리스트 조회를 할 수 있다.")
    void readPosts_LoginUser() {
        // given
        token = getToken(author.getUsername());
        savePost(postRequest);

        // when
        ExtractableResponse<Response> response = readPosts(postRequest.getChildCategory(), 20L);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    private ExtractableResponse<Response> readPostDetails(Long postId) {
        return RestAssured
                .given().log().all()
                .accept("application/json")
                .header(AUTHORIZATION, AuthorizationExtractor.BEARER_TYPE + " " + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/api/posts/{postId}", postId)
                .then().log().all().extract();
    }

    private ExtractableResponse<Response> readPosts(ChildCategory category, Long postId) {
        String url;
        if (Objects.isNull(postId)) {
            url = String.format(READ_POSTS_DEFAULT_URL, category.getName());
        } else {
            url = String.format(READ_POSTS_URL, category.getName(), postId);
        }

        return RestAssured
                .given().log().all()
                .accept("application/json")
                .header(AUTHORIZATION, AuthorizationExtractor.BEARER_TYPE + " " + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get(url)
                .then().log().all().extract();
    }
}