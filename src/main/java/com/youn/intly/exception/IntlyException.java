package com.youn.intly.exception;

import org.springframework.http.HttpStatus;

public class IntlyException extends RuntimeException {

    private final HttpStatus httpStatus;
    private final ErrorType errorType = ErrorType.of(getClass());

    public IntlyException(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public IntlyException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}