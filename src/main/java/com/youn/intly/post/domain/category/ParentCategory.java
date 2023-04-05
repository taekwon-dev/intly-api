package com.youn.intly.post.domain.category;

import com.youn.intly.exception.post.IllegalCategoryException;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum ParentCategory {

    FREE_TOPIC("free-topic"),
    FILEDS("fields"),
    MY_CAMPUS("my-campus")
    ;

    private final String name;

    ParentCategory(final String name) {
        this.name = name;
    }

    public static ParentCategory of(final String name) {
        return Arrays.stream(values())
                .filter(category -> category.name.equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow(IllegalCategoryException::new);
    }
}