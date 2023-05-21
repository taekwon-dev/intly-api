package com.youn.intly.exception.tag;

import com.youn.intly.exception.IntlyException;
import org.springframework.http.HttpStatus;

public class TagFormatException extends IntlyException {

    private static final String MESSAGE = "태그 길이 제한을 초과했습니다.";

    public TagFormatException() {
        super(MESSAGE, HttpStatus.BAD_REQUEST);
    }
}