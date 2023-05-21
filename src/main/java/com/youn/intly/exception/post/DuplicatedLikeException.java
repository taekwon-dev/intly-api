package com.youn.intly.exception.post;

import com.youn.intly.exception.IntlyException;
import org.springframework.http.HttpStatus;

public class DuplicatedLikeException extends IntlyException {

    private static final String MESSAGE = "이미 좋아요 처리가 완료된 게시글입니다.";

    public DuplicatedLikeException() {
        super(MESSAGE, HttpStatus.BAD_REQUEST);
    }
}