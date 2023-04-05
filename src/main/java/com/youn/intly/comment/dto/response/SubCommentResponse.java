package com.youn.intly.comment.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class SubCommentResponse {

    private Long id;
    private String parentUsername;
    private String username;
    private String content;
    private boolean mine;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private SubCommentResponse() {
    }

    @Builder
    public SubCommentResponse(
        final Long id,
        final String parentUsername,
        final String username,
        final String content,
        final boolean mine,
        final LocalDateTime createdAt,
        final LocalDateTime updatedAt
    ) {
        this.id = id;
        this.parentUsername = parentUsername;
        this.username = username;
        this.content = content;
        this.mine = mine;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}