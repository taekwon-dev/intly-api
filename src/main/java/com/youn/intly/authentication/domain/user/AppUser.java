package com.youn.intly.authentication.domain.user;

public abstract class AppUser {

    private final String username;

    protected AppUser(final String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public abstract boolean isGuest();
}