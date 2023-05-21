package com.youn.intly.exception.post;

import com.youn.intly.exception.IntlyException;
import org.springframework.http.HttpStatus;

public class PostNotFoundException extends IntlyException {

    private static final String MESSAGE = "존재하지 않는 게시글입니다.";

    public PostNotFoundException() {
        super(MESSAGE, HttpStatus.NOT_FOUND);
    }
}