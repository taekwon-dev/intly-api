package com.youn.intly.exception.http;

import com.youn.intly.exception.IntlyException;
import org.springframework.http.HttpStatus;

public class BadRequestException extends IntlyException {

    public BadRequestException() {
        super(HttpStatus.BAD_REQUEST);
    }
}