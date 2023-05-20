package com.youn.intly.exception;

import org.springframework.http.HttpStatus;

public class IntlyException extends RuntimeException {

    private final HttpStatus httpStatus;

    public IntlyException(final HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public IntlyException(final String message, final HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}