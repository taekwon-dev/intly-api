package com.youn.intly.exception.http;

import com.youn.intly.exception.IntlyException;
import org.springframework.http.HttpStatus;

public class NotFoundException extends IntlyException {

    public NotFoundException() {
        super(HttpStatus.NOT_FOUND);
    }
}