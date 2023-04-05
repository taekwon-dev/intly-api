package com.youn.intly.acceptance;

import com.youn.intly.DatabaseCleaner;
import com.youn.intly.authentication.application.JWTTokenProvider;
import com.youn.intly.authentication.infrastructure.AuthorizationExtractor;
import com.youn.intly.comment.dto.request.CommentRequest;
import com.youn.intly.comment.dto.request.SubCommentRequest;
import com.youn.intly.post.dto.request.PostRequest;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import io.restassured.RestAssured;

import static com.youn.intly.authentication.infrastructure.AuthorizationExtractor.AUTHORIZATION;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AcceptanceTest {

    protected static String token;

    @LocalServerPort
    private int port;

    @Autowired
    private JWTTokenProvider jwtTokenProvider;

    @Autowired
    private DatabaseCleaner databaseCleaner;

    @BeforeEach
    void init() {
        RestAssured.port = port;
        saveUser();
    }

    @AfterEach
    void destroy() {
        databaseCleaner.execute();
    }

    protected final String getToken(String payload) {
        return jwtTokenProvider.createToken(payload);
    }

    protected ExtractableResponse<Response> saveUser() {
        return RestAssured
                .given()
                .accept("application/json")
                .when().post("/api/users/")
                .then().log().all().extract();
    }

    protected ExtractableResponse<Response> savePost(final PostRequest postRequest) {
        return RestAssured
                .given()
                .accept("application/json")
                .header(AUTHORIZATION, AuthorizationExtractor.BEARER_TYPE + " " + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(postRequest)
                .when().post("/api/posts")
                .then().log().all().extract();
    }

    protected static ExtractableResponse<Response> saveComment(
        final Long postId,
        final CommentRequest commentRequest
    ) {
        return RestAssured
                .given()
                .accept("application/json")
                .header(AUTHORIZATION, AuthorizationExtractor.BEARER_TYPE + " " + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(commentRequest)
                .when().post("/api/posts/{postId}/comments", postId)
                .then().log().all().extract();
    }

    protected static ExtractableResponse<Response> saveSubComment(
        final Long postId,
        final Long rootParentId,
        final SubCommentRequest subCommentRequest
    ) {
        return RestAssured
                .given()
                .accept("application/json")
                .header(AUTHORIZATION, AuthorizationExtractor.BEARER_TYPE + " " + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(subCommentRequest)
                .when().post("/api/posts/{postId}/comments/{rootParentId}", postId, rootParentId)
                .then().log().all().extract();
    }
}