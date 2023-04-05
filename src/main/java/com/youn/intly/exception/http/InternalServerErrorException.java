package com.youn.intly.exception.http;

import com.youn.intly.exception.IntlyException;
import org.springframework.http.HttpStatus;

public class InternalServerErrorException extends IntlyException {

    private final String message;

    public InternalServerErrorException(String message) {
        super(HttpStatus.INTERNAL_SERVER_ERROR);
        this.message = message;
    }
}