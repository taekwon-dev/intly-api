package com.youn.intly.exception.http;

import com.youn.intly.exception.IntlyException;
import org.springframework.http.HttpStatus;

public class ExternalException extends IntlyException {

    public ExternalException() {
        super(HttpStatus.BAD_REQUEST);
    }
}
