package com.youn.intly.post.domain.category;

import com.youn.intly.exception.post.IllegalCategoryException;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum ChildCategory {

    FREE_THREAD("free-thread"),
    WORRIES_HUGS("worries-hugs"),
    TECH("tech"),
    BUSINESS("business"),
    LAW("law"),
    MED_HEALTH("med-health"),
    HUMANITIES("humanities"),
    SOCIAL_SCIENCES("social-sciences"),
    NATURAL_SCIENCES("natural-sciences"),
    ARTS("arts"),
    EVENTS("events"),
    CAMPUS("campus")
    ;

    private final String name;

    ChildCategory(final String name) {
        this.name = name;
    }

    public static ChildCategory of(final String name) {
        return Arrays.stream(values())
                .filter(category -> category.name.equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow(IllegalCategoryException::new);
    }
}