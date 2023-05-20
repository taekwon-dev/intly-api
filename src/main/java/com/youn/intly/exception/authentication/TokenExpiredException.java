package com.youn.intly.exception.authentication;

import com.youn.intly.exception.IntlyException;
import org.springframework.http.HttpStatus;

public class TokenExpiredException extends IntlyException {

    private static final String MESSAGE =  "로그인 인증 유효기간이 만료되었습니다. 다시 로그인 해주세요.";

    public TokenExpiredException() {
        super(MESSAGE, HttpStatus.UNAUTHORIZED);
    }
}