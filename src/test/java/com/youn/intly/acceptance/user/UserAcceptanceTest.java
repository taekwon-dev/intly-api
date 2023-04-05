package com.youn.intly.acceptance.user;

import com.youn.intly.acceptance.AcceptanceTest;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;

public class UserAcceptanceTest extends AcceptanceTest {

    @Test
    @DisplayName("[테스트 목적] 회원 등록")
    void enroll() {
        // then

        // when
        ExtractableResponse<Response> response = saveUser();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }
}