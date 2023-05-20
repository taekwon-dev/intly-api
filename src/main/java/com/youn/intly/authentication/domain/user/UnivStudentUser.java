package com.youn.intly.authentication.domain.user;

public class UnivStudentUser extends AppUser {

    public UnivStudentUser(final String username) {
        super(username);
    }

    @Override
    public boolean isGuest() {
        return false;
    }

    @Override
    public boolean isUnivStudent() {
        return true;
    }
}