package com.youn.intly.post.domain.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.youn.intly.post.domain.Post;
import com.youn.intly.post.domain.category.ChildCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.youn.intly.post.domain.QPost.post;

@RequiredArgsConstructor
@Repository
public class PostFeedRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public List<Post> readPosts(ChildCategory category, Long lastPostId) {
        return jpaQueryFactory.selectFrom(post)
                .where(
                        ltPostId(lastPostId),
                        post.childCategory.eq(category)
                )
                .orderBy(post.createdAt.desc())
                .limit(20)
                .fetch();
    }

    private BooleanExpression ltPostId(Long lastPostId) {
        if (lastPostId == null) {
            return null;
        }
        return post.id.lt(lastPostId);
    }
}