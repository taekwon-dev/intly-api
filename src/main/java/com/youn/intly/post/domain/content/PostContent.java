package com.youn.intly.post.domain.content;

import com.youn.intly.exception.post.PostContentFormatException;
import lombok.Getter;

import javax.persistence.Embeddable;
import javax.persistence.Lob;
import java.util.Objects;

@Getter
@Embeddable
public class PostContent {

    private static final int MAXIMUM_CONTENT_LENGTH = 1_000;

    @Lob
    private String content;

    protected PostContent() {
    }

    public PostContent(final String content) {
        validateLengthOf(content);
        this.content = content;
    }

    private void validateLengthOf(final String content) {
        if (content.isBlank() || content.length() > MAXIMUM_CONTENT_LENGTH) {
            throw new PostContentFormatException();
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
        PostContent content = (PostContent) o;
        return Objects.equals(content, content.getContent());
    }

    @Override
    public int hashCode() {
        return Objects.hash(content);
    }
}