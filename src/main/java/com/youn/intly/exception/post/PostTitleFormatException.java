package com.youn.intly.exception.post;

import com.youn.intly.exception.IntlyException;
import org.springframework.http.HttpStatus;

public class PostTitleFormatException extends IntlyException {

    private static final String MESSAGE = "작성할 수 있는 게시글 제목 길이를 초과했습니다.";

    public PostTitleFormatException() {
        super(MESSAGE, HttpStatus.BAD_REQUEST);
    }
}