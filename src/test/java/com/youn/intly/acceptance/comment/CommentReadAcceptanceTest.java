package com.youn.intly.acceptance.comment;

import com.youn.intly.acceptance.AcceptanceTest;
import com.youn.intly.authentication.infrastructure.AuthorizationExtractor;
import com.youn.intly.comment.dto.request.CommentRequest;
import com.youn.intly.comment.dto.request.SubCommentRequest;
import com.youn.intly.comment.dto.response.CommentResponse;
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

public class CommentReadAcceptanceTest extends AcceptanceTest {

    private User author;
    private PostRequest postRequest;
    private CommentRequest commentRequest;

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

        commentRequest = new CommentRequest("comment content");
    }

    @Test
    @DisplayName("로그인 유저는 포스트 상세 조회 시 댓글 리스트를 조회할 수 있다.")
    void readComments_LoginUser() {
        // given
        token = getToken(author.getUsername());
        Long postId = Long.parseLong(savePost(postRequest).header("location").split("/")[2]);
        saveComment(postId, commentRequest);

        // when
        ExtractableResponse<Response> response = readComments(postId);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("로그인 유저는 포스트 상세 조회 시 댓글에 달린 답글 리스트를 조회할 수 있다.")
    void readSubComments_LoginUser() {
        // given
        token = getToken(author.getUsername());
        Long postId = Long.parseLong(savePost(postRequest).header("location").split("/")[2]);
        Long commentId = saveComment(postId, commentRequest).body().as(CommentResponse.class).getId();
        saveSubComment(postId, commentId, new SubCommentRequest(commentId, "sub comment content"));

        // when
        ExtractableResponse<Response> response = readSubComments(postId, commentId);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    private ExtractableResponse<Response> readComments(Long postId) {
        return RestAssured
                .given().log().all()
                .accept("application/json")
                .header(AUTHORIZATION, AuthorizationExtractor.BEARER_TYPE + " " + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/api/posts/{postId}/comments", postId)
                .then().log().all().extract();
    }

    private ExtractableResponse<Response> readSubComments(Long postId, Long commentId) {
        return RestAssured
                .given().log().all()
                .accept("application/json")
                .header(AUTHORIZATION, AuthorizationExtractor.BEARER_TYPE + " " + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/api/posts/{postId}/comments/{commentId}/subcomments", postId, commentId)
                .then().log().all().extract();
    }
}