package com.youn.intly.acceptance.comment;

import com.youn.intly.acceptance.AcceptanceTest;
import com.youn.intly.authentication.infrastructure.AuthorizationExtractor;
import com.youn.intly.comment.dto.request.CommentRequest;
import com.youn.intly.comment.dto.request.SubCommentRequest;
import com.youn.intly.comment.dto.response.LeaveCommentResponse;
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

class CommentAcceptanceTest extends AcceptanceTest {

    private User author;
    private PostRequest postRequest;
    private Long postId;
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
                .tags(List.of("java", "spring"))
                .build();

        token = getToken(author.getUsername());
        postId = Long.parseLong(savePost(postRequest).header("location").split("/")[2]);
        commentRequest = new CommentRequest("content");
    }

    @Test
    @DisplayName("로그인 유저는 댓글을 작성할 수 있다.")
    void writeComment_LoginUser() {
        // given
        token = getToken(author.getUsername());

        // when
        ExtractableResponse<Response> response = saveComment(postId, commentRequest);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("로그인 유저는 답글을 작성할 수 있다.")
    void writeSubComment_LoginUser() {
        // given
        token = getToken(author.getUsername());
        ExtractableResponse<Response> commentResponse = saveComment(postId, commentRequest);
        Long rootParentId = commentResponse.as(LeaveCommentResponse.class).getId();
        Long parentId = rootParentId;
        SubCommentRequest subCommentRequest = new SubCommentRequest(parentId, "content");

        // when
        ExtractableResponse<Response> response = saveSubComment(postId, rootParentId, subCommentRequest);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }
}