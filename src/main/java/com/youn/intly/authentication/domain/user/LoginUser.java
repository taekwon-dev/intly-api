package com.youn.intly.authentication.domain.user;

public class LoginUser extends AppUser {

    public LoginUser(final String username) {
        super(username);
    }

    @Override
    public boolean isGuest() {
        return false;
    }
}