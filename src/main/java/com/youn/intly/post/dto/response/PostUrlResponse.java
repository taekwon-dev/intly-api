package com.youn.intly.post.dto.response;

import lombok.Getter;

@Getter
public class PostUrlResponse {

    private Long id;

    private PostUrlResponse() {
    }

    public PostUrlResponse(final Long id) {
        this.id = id;
    }
}