package com.youn.intly.post.dto.response;

import lombok.Getter;

@Getter
public class LikePostResponse {

    private int likesCount;
    private Boolean liked;

    private LikePostResponse() {
    }

    public LikePostResponse(final int likeCount, final boolean liked) {
        this.likesCount = likeCount;
        this.liked = liked;
    }
}