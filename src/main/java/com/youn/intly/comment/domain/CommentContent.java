package com.youn.intly.comment.domain;

import com.youn.intly.exception.comment.CommentContentFormatException;
import lombok.Getter;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
@Getter
public class CommentContent {

    private static final int MAXIMUM_CONTENT_LENGTH = 100;

    private String content;

    protected CommentContent() {
    }

    public CommentContent(final String content) {
        validateLengthOf(content);
        this.content = content;
    }

    private void validateLengthOf(final String content) {
        if (content.isBlank() || content.length() > MAXIMUM_CONTENT_LENGTH) {
            throw new CommentContentFormatException();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CommentContent content = (CommentContent) o;
        return Objects.equals(this.content, content.getContent());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.content);
    }
}