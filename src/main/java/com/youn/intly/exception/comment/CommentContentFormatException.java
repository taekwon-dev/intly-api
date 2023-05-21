package com.youn.intly.exception.comment;

import com.youn.intly.exception.IntlyException;
import org.springframework.http.HttpStatus;

public class CommentContentFormatException extends IntlyException {

    private static final String MESSAGE = "작성할 수 있는 댓글 길이를 초과했습니다.";

    public CommentContentFormatException() {
        super(MESSAGE, HttpStatus.BAD_REQUEST);
    }
}