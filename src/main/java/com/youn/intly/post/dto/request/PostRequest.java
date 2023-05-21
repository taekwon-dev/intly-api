package com.youn.intly.post.dto.request;

import com.youn.intly.post.domain.category.ChildCategory;
import com.youn.intly.post.domain.category.ParentCategory;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class PostRequest {

    private ParentCategory parentCategory;
    private ChildCategory childCategory;
    private String title;
    private String content;
    private List<String> tags;

    private PostRequest() {
    }

    @Builder
    public PostRequest(
        final ParentCategory parentCategory,
        final ChildCategory childCategory,
        final String title,
        final String content,
        final List<String> tags
    ) {
        this.parentCategory = parentCategory;
        this.childCategory = childCategory;
        this.title = title;
        this.content = content;
        this.tags = tags;
    }
}