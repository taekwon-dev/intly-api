package com.youn.intly.exception.authentication;

import com.youn.intly.exception.IntlyException;
import org.springframework.http.HttpStatus;

public class InvalidTokenException extends IntlyException {

    private static final String MESSAGE = "로그인이 필요한 서비스입니다.";

    public InvalidTokenException() {
        super(MESSAGE, HttpStatus.UNAUTHORIZED);
    }
}