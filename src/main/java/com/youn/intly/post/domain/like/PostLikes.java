package com.youn.intly.post.domain.like;

import com.youn.intly.exception.post.CannotUnlikeException;
import com.youn.intly.exception.post.DuplicatedLikeException;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Embeddable
public class PostLikes {

    @OneToMany(
            mappedBy = "post",
            fetch = FetchType.LAZY,
            cascade = CascadeType.PERSIST
    )
    private List<PostLike> likes;

    public PostLikes() {
        this(new ArrayList<>());
    }

    public PostLikes(final List<PostLike> likes) {
        this.likes = likes;
    }

    public int getCount() {
        return likes.size();
    }

    public boolean contains(final PostLike like) {
        return likes.contains(like);
    }

    public void add(final PostLike like) {
        if (likes.contains(like)) {
            throw new DuplicatedLikeException();
        }
        likes.add(like);
    }

    public void remove(PostLike like) {
        if (!likes.contains(like)) {
            throw new CannotUnlikeException();
        }
        likes.remove(like);
    }
}