package com.youn.intly.post.domain.like;

import com.youn.intly.post.domain.Post;
import com.youn.intly.user.domain.User;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "post_likes")
public class PostLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    protected PostLike() {
    }

    public PostLike(final Post post, final User user) {
        this(null, post, user);
    }

    public PostLike(final Long id, final Post post, final User user) {
        this.id = id;
        this.post = post;
        this.user = user;
    }

    public Post getPost() {
        return post;
    }

    public User getUser() {
        return user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PostLike like = (PostLike) o;
        return Objects.equals(post, like.getPost()) && Objects.equals(user, like.getUser());
    }

    @Override
    public int hashCode() {
        return Objects.hash(post, user);
    }
}