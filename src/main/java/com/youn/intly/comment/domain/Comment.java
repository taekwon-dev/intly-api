package com.youn.intly.comment.domain;

import com.youn.intly.post.domain.Post;
import com.youn.intly.user.domain.User;
import lombok.Builder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@EntityListeners(AuditingEntityListener.class)
@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "root_parent_id")
    private Comment rootParent;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Comment parent;

    @Embedded
    private CommentContent content;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    protected Comment() {
    }

    @Builder
    public Comment(
        final Long id,
        final Post post,
        final User user,
        final Comment rootParent,
        final Comment parent,
        final String content,
        final LocalDateTime createdAt,
        final LocalDateTime updatedAt
    ) {
        this.id = id;
        this.post = post;
        this.user = user;
        this.rootParent = rootParent;
        this.parent = parent;
        this.content = new CommentContent(content);
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public Long getParentId() {
        return parent.getId();
    }

    public User getUser() {
        return user;
    }

    public String getUsername() {
        return user.getUsername();
    }

    public String getParentUsername() {
        return parent.getUsername();
    }

    public String getContent() {
        return content.getContent();
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public boolean isComment() {
        return Objects.isNull(parent);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Comment comment = (Comment) o;
        return Objects.equals(id, comment.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}