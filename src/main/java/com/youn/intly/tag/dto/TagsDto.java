package com.youn.intly.tag.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class TagsDto {

    private List<String> tagNames;

    protected TagsDto() {
    }

    public TagsDto(final List<String> tagNames) {
        this.tagNames = tagNames;
    }
}