package com.youn.intly.post.domain;

import com.youn.intly.post.domain.category.ChildCategory;
import com.youn.intly.post.domain.category.ParentCategory;
import com.youn.intly.post.domain.content.PostContent;
import com.youn.intly.post.domain.content.PostTitle;
import com.youn.intly.post.domain.like.PostLike;
import com.youn.intly.post.domain.like.PostLikes;
import com.youn.intly.post.domain.tag.PostTags;
import com.youn.intly.tag.domain.Tag;
import com.youn.intly.user.domain.User;
import lombok.Builder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@EntityListeners(AuditingEntityListener.class)
@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User author;

    @Enumerated(EnumType.STRING)
    private ParentCategory parentCategory;

    @Enumerated(EnumType.STRING)
    private ChildCategory childCategory;

    @Embedded
    private PostTitle title;

    @Embedded
    private PostContent content;

    @Embedded
    private PostTags tags;

    @Embedded
    private PostLikes likes;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    protected Post() {
    }

    @Builder
    public Post(
        final Long id,
        final User author,
        final ParentCategory parentCategory,
        final ChildCategory childCategory,
        final PostTitle title,
        final PostContent content,
        final LocalDateTime createdAt,
        final LocalDateTime updatedAt
    ) {
        this.id = id;
        this.author = author;
        this.parentCategory = parentCategory;
        this.childCategory = childCategory;
        this.title = title;
        this.content = content;
        this.tags = new PostTags();
        this.likes = new PostLikes();
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public User getAuthor() {
        return author;
    }

    public String getAuthorName() {
        return author.getUsername();
    }

    public String getTitle() {
        return title.getTitle();
    }

    public String getContent() {
        return content.getContent();
    }

    public ParentCategory getParentCategory() {
        return parentCategory;
    }

    public ChildCategory getChildCategory() {
        return childCategory;
    }

    public List<String> getTagNames() {
        return tags.getTagNames();
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void addTags(final List<Tag> tags) {
        this.tags.addAll(this, tags);
    }

    public int getLikeCount() {
        return likes.getCount();
    }

    public void like(User user) {
        PostLike like = new PostLike(this, user);
        likes.add(like);
    }

    public void cancelLike(User user) {
        PostLike like = new PostLike(this, user);
        likes.remove(like);
    }

    public boolean isLikedBy(final User user) {
        PostLike like = new PostLike(this, user);
        return likes.contains(like);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Post post = (Post) o;
        return Objects.equals(id, post.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}