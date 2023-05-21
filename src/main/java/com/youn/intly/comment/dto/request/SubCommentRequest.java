package com.youn.intly.comment.dto.request;

import lombok.Getter;

@Getter
public class SubCommentRequest {

    private Long parentId;
    private String content;

    private SubCommentRequest() {
    }

    public SubCommentRequest(final Long parentId, final String content) {
        this.parentId = parentId;
        this.content = content;
    }
}