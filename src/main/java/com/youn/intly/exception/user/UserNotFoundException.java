package com.youn.intly.exception.user;

import com.youn.intly.exception.IntlyException;
import org.springframework.http.HttpStatus;

public class UserNotFoundException extends IntlyException {

    private static final String MESSAGE = "";

    public UserNotFoundException() {
        super(MESSAGE, HttpStatus.NOT_FOUND);
    }
}