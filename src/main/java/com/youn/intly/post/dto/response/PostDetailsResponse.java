package com.youn.intly.post.dto.response;

import com.youn.intly.post.domain.category.ChildCategory;
import com.youn.intly.post.domain.category.ParentCategory;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class PostDetailsResponse {

    private Long id;
    private ParentCategory parentCategory;
    private ChildCategory childCategory;
    private String authorName;
    private String title;
    private String content;
    private List<String> tags;
    private boolean mine;
    private boolean liked;
    private int likesCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private PostDetailsResponse() {
    }

    @Builder
    public PostDetailsResponse(
        final Long id,
        final ParentCategory parentCategory,
        final ChildCategory childCategory,
        final String authorName,
        final String title,
        final String content,
        final List<String> tags,
        final boolean mine,
        final boolean liked,
        final int likesCount,
        final LocalDateTime createdAt,
        final LocalDateTime updatedAt
    ) {
        this.id = id;
        this.parentCategory = parentCategory;
        this.childCategory = childCategory;
        this.authorName = authorName;
        this.title = title;
        this.content = content;
        this.tags = tags;
        this.mine = mine;
        this.liked = liked;
        this.likesCount = likesCount;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}