package com.youn.intly.comment.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class LeaveCommentResponse {

    private Long id;
    private String username;
    private String content;
    private boolean mine;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private LeaveCommentResponse() {
    }

    @Builder
    public LeaveCommentResponse(
        final Long id,
        final String username,
        final String content,
        final boolean mine,
        final LocalDateTime createdAt,
        final LocalDateTime updatedAt
    ) {
        this.id = id;
        this.username = username;
        this.content = content;
        this.mine = mine;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}