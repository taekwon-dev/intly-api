package com.youn.intly.unit.post.content;

import com.youn.intly.exception.post.PostContentFormatException;
import com.youn.intly.exception.post.PostTitleFormatException;
import com.youn.intly.post.domain.content.PostContent;
import com.youn.intly.post.domain.content.PostTitle;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class PostTitleTest {

    @Test
    @DisplayName("포스트 제목이 100자 초과하는 경우, 예외가 발생한다.")
    void validateIsOver1000_ExceptionThrown() {
        String title = "s".repeat(101);

        assertThatThrownBy(() -> new PostTitle(title))
                .isInstanceOf(PostTitleFormatException.class)
                .hasFieldOrPropertyWithValue("httpStatus", HttpStatus.BAD_REQUEST);
    }
}