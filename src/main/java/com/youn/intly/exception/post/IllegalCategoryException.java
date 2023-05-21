package com.youn.intly.exception.post;

import com.youn.intly.exception.IntlyException;
import org.springframework.http.HttpStatus;

public class IllegalCategoryException extends IntlyException {

    private static final String MESSAGE = "게시글 카테고리를 찾을 수 없습니다.";

    public IllegalCategoryException() {
        super(MESSAGE, HttpStatus.BAD_REQUEST);
    }
}