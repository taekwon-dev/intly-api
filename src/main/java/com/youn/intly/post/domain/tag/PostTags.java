package com.youn.intly.post.domain.tag;

import com.youn.intly.exception.tag.CannotAddTagException;
import com.youn.intly.post.domain.Post;
import com.youn.intly.tag.domain.Tag;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Embeddable
public class PostTags {

    @OneToMany(
            mappedBy = "post",
            fetch = FetchType.LAZY,
            cascade = CascadeType.PERSIST
    )
    private List<PostTag> postTags;

    public PostTags() {
        this(new ArrayList<>());
    }

    public PostTags(List<PostTag> postTags) {
        this.postTags = postTags;
    }

    public void add(Post post, Tag tag) {
        addAll(post, List.of(tag));
    }

    public void addAll(Post post, List<Tag> tags) {
        validateDuplicationOf(tags);
        tags.stream()
                .map(tag -> new PostTag(post, tag))
                .forEach(postTags::add);
    }

    private void validateDuplicationOf(List<Tag> tags) {
        long distinctNumberOfTags = tags.stream()
                .map(Tag::getName)
                .distinct()
                .count();
        if (distinctNumberOfTags != tags.size()) {
            throw new CannotAddTagException();
        }
    }

    public List<Tag> getTags() {
        return postTags.stream()
                .map(PostTag::getTag)
                .collect(toList());
    }

    public List<String> getTagNames() {
        return getTags().stream()
                .map(Tag::getName)
                .collect(toList());
    }
}