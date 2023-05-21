package com.youn.intly.tag.domain;

import com.youn.intly.exception.tag.TagFormatException;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Tag {

    private static final int MAX_TAG_LENGTH = 20;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    protected Tag() {
    }

    public Tag(final String name) {
        validateLengthOf(name);
        this.name = name;
    }

    private void validateLengthOf(final String name) {
        if (name.isBlank() || name.length() > MAX_TAG_LENGTH) {
            throw new TagFormatException();
        }
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Tag tag = (Tag) o;
        return Objects.equals(id, tag.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}