package com.youn.intly.exception.comment;

import com.youn.intly.exception.IntlyException;
import org.springframework.http.HttpStatus;

public class CommentNotFoundException extends IntlyException {

    private static final String MESSAGE = "존재하지 않는 댓글입니다.";

    public CommentNotFoundException() {
        super(MESSAGE, HttpStatus.NOT_FOUND);
    }
}