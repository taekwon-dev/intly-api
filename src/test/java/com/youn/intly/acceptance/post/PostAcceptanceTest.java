package com.youn.intly.acceptance.post;

import com.youn.intly.acceptance.AcceptanceTest;
import com.youn.intly.authentication.infrastructure.AuthorizationExtractor;
import com.youn.intly.comment.dto.request.CommentRequest;
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

import static com.youn.intly.authentication.infrastructure.AuthorizationExtractor.AUTHORIZATION;
import static org.assertj.core.api.Assertions.assertThat;

class PostAcceptanceTest extends AcceptanceTest {

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
                .tags(List.of("java", "spring"))
                .build();
    }

    @Test
    @DisplayName("로그인 유저는 포스트를 작성할 수 있다.")
    void write() {
        // given
        token = getToken(author.getUsername());

        // when
        ExtractableResponse<Response> response = savePost(postRequest);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    @Test
    @DisplayName("로그인 유저는 포스트에 좋아요 할 수 있다.")
    void like() {
        // given
        token = getToken(author.getUsername());
        Long postId = Long.parseLong(savePost(postRequest).header("location").split("/")[2]);

        // when
        ExtractableResponse<Response> response = savePostLike(postId);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("로그인 유저는 포스트에 좋아요 취소 할 수 있다.")
    void cancelLike() {
        // given
        token = getToken(author.getUsername());
        Long postId = Long.parseLong(savePost(postRequest).header("location").split("/")[2]);
        savePostLike(postId);

        // when
        ExtractableResponse<Response> response = cancelPostLike(postId);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("로그인 유저는 본인이 작성한 포스트를 삭제할 수 있다.")
    void delete() {
        // given
        token = getToken(author.getUsername());
        Long postId = Long.parseLong(savePost(postRequest).header("location").split("/")[2]);

        // when
        ExtractableResponse<Response> response = deletePost(postId);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());

    }

    protected static ExtractableResponse<Response> savePostLike(final Long postId) {
        return RestAssured
                .given()
                .accept("application/json")
                .header(AUTHORIZATION, AuthorizationExtractor.BEARER_TYPE + " " + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().put("/api/posts/{postId}/likes", postId)
                .then().log().all().extract();
    }

    protected static ExtractableResponse<Response> cancelPostLike(final Long postId) {
        return RestAssured
                .given()
                .accept("application/json")
                .header(AUTHORIZATION, AuthorizationExtractor.BEARER_TYPE + " " + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().delete("/api/posts/{postId}/likes", postId)
                .then().log().all().extract();
    }

    protected static ExtractableResponse<Response> deletePost(final Long postId) {
        return RestAssured
                .given()
                .header(AUTHORIZATION, AuthorizationExtractor.BEARER_TYPE + " " + token)
                .when().delete("/api/posts/{postId}", postId)
                .then().log().all().extract();
    }
}