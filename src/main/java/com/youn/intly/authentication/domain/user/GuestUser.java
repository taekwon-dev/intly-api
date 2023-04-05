package com.youn.intly.authentication.domain.user;

import com.youn.intly.exception.http.UnAuthorizedException;

public class GuestUser extends AppUser {

    private static final String DUMMY_USERNAME = "anonymous";

    public GuestUser() {
        super(DUMMY_USERNAME);
    }

    @Override
    public String getUsername() {
        throw new UnAuthorizedException();
    }

    @Override
    public boolean isGuest() {
        return true;
    }
}