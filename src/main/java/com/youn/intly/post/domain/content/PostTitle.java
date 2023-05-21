package com.youn.intly.post.domain.content;

import com.youn.intly.exception.post.PostTitleFormatException;
import lombok.Getter;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
@Getter
public class PostTitle {

    private static final int MAXIMUM_TITLE_LENGTH = 100;

    private String title;

    protected PostTitle() {
    }

    public PostTitle(final String title) {
        validateLengthOf(title);
        this.title = title;
    }

    private void validateLengthOf(final String title) {
        if (title.isBlank() || title.length() > MAXIMUM_TITLE_LENGTH) {
            throw new PostTitleFormatException();
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
        PostTitle title = (PostTitle) o;
        return Objects.equals(title, title.getTitle());
    }

    @Override
    public int hashCode() {
        return Objects.hash(title);
    }
}