package com.youn.intly.exception;

import com.youn.intly.exception.http.InternalServerErrorException;

public class UndefinedException extends InternalServerErrorException {

    public UndefinedException(String message) {
        super(message);
    }
}