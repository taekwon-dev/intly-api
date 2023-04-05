package com.youn.intly.authentication.dto;

import lombok.Getter;

@Getter
public class TokenResponse {

    private String token;
    private String username;

    private TokenResponse() {
    }

    public TokenResponse(final String token, final String username) {
        this.token = token;
        this.username = username;
    }
}