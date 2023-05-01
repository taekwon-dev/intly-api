package com.youn.intly.unit.post.content;

import com.youn.intly.exception.post.PostContentFormatException;
import com.youn.intly.post.domain.content.PostContent;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class PostContentTest {

    @Test
    @DisplayName("포스트 내용이 1000자 초과하는 경우, 예외가 발생한다.")
    void validateIsOver1000_ExceptionThrown() {
        String content = "s".repeat(1_001);

        assertThatThrownBy(() -> new PostContent(content))
                .isInstanceOf(PostContentFormatException.class)
                .hasFieldOrPropertyWithValue("httpStatus", HttpStatus.BAD_REQUEST);
    }
}