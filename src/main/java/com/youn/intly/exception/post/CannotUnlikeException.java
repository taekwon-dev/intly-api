package com.youn.intly.exception.post;

import com.youn.intly.exception.IntlyException;
import org.springframework.http.HttpStatus;

public class CannotUnlikeException extends IntlyException {

    private static final String MESSAGE = "좋아요 하지 않은 게시글에 좋아요 취소를 요청할 수 없습니다.";

    public CannotUnlikeException() {
        super(MESSAGE, HttpStatus.BAD_REQUEST);
    }
}