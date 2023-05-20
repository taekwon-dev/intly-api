package com.youn.intly.authentication.domain.user;

import com.youn.intly.exception.authentication.InvalidTokenException;

public class GuestUser extends AppUser {

    private static final String DUMMY_USERNAME = "anonymous";

    public GuestUser() {
        super(DUMMY_USERNAME);
    }

    @Override
    public String getUsername() {
        throw new InvalidTokenException();
    }

    @Override
    public boolean isGuest() {
        return true;
    }

    @Override
    public boolean isUnivStudent() {
        return false;
    }
}