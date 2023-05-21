package com.youn.intly.exception.tag;

import com.youn.intly.exception.IntlyException;
import org.springframework.http.HttpStatus;

public class CannotAddTagException extends IntlyException {

    private static final String MESSAGE = "게시글에 태그를 등록할 수 없습니다.";

    public CannotAddTagException() {
        super(MESSAGE, HttpStatus.BAD_REQUEST);
    }
}