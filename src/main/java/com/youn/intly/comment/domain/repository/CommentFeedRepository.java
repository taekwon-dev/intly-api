package com.youn.intly.comment.domain.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.youn.intly.comment.domain.Comment;
import com.youn.intly.post.domain.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.youn.intly.comment.domain.QComment.comment;

@RequiredArgsConstructor
@Repository
public class CommentFeedRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public List<Comment> readComments(final Post post, final Long commentId) {
        return jpaQueryFactory
                .selectFrom(comment)
                .where(
                        ltCommentId(commentId),
                        comment.post.eq(post),
                        comment.rootParent.isNull(),
                        comment.parent.isNull()
                )
                .orderBy(comment.createdAt.desc())
                .limit(20)
                .fetch();
    }

    private BooleanExpression ltCommentId(final Long lastCommentId) {
        if (lastCommentId == null) {
            return null;
        }
        return comment.id.lt(lastCommentId);
    }

    public List<Comment> readSubComments(
        final Post post,
        final Comment rootParent,
        final Long subCommentId
    ) {
        return jpaQueryFactory
                .selectFrom(comment)
                .where(
                        ltSubCommentId(subCommentId),
                        comment.post.eq(post),
                        comment.rootParent.eq(rootParent)
                )
                .orderBy(comment.createdAt.desc())
                .limit(20)
                .fetch();
    }

    private BooleanExpression ltSubCommentId(final Long lastSubCommentId) {
        if (lastSubCommentId == null) {
            return null;
        }
        return comment.id.lt(lastSubCommentId);
    }
}