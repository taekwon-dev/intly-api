package com.youn.intly.exception.http;

import com.youn.intly.exception.IntlyException;
import org.springframework.http.HttpStatus;

public class UnAuthorizedException extends IntlyException {

    public UnAuthorizedException() {
        super(HttpStatus.UNAUTHORIZED);
    }
}