package com.youn.intly.post.application;

import com.youn.intly.authentication.domain.user.AppUser;
import com.youn.intly.exception.post.PostNotFoundException;
import com.youn.intly.exception.user.UserNotFoundException;
import com.youn.intly.post.domain.Post;
import com.youn.intly.post.domain.category.ChildCategory;
import com.youn.intly.post.domain.repository.PostFeedRepository;
import com.youn.intly.post.domain.repository.PostRepository;
import com.youn.intly.post.dto.response.PostDetailsResponse;
import com.youn.intly.post.dto.response.PostResponse;
import com.youn.intly.user.domain.User;
import com.youn.intly.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class PostFeedService {

    private final PostRepository postRepository;
    private final PostFeedRepository postFeedRepository;
    private final UserRepository userRepository;

    public PostDetailsResponse readPostDetails(
        final AppUser user,
        final Long postId
    ) {
        Post post = findPostById(postId);
        if (user.isGuest()) {
            return createPostDetailsResponse(post, null);
        }
        User requestUser = findUserByUsername(user.getUsername());
        return createPostDetailsResponse(post, requestUser);
    }

    private PostDetailsResponse createPostDetailsResponse(
        final Post post,
        final User requestUser
    ) {
        return PostDetailsResponse.builder()
                .id(post.getId())
                .parentCategory(post.getParentCategory())
                .childCategory(post.getChildCategory())
                .authorName(post.getAuthorName())
                .title(post.getTitle())
                .content(post.getContent())
                .tags(post.getTagNames())
                .mine(isMine(post.getAuthor(), requestUser))
                .liked(isLikedBy(post, requestUser))
                .likesCount(post.getLikeCount())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .build();
    }

    public List<PostResponse> readPosts(
        final AppUser user,
        final String category,
        final Long postId
    ) {
        User requestUser = findUserByUsername(user.getUsername());
        List<Post> posts = postFeedRepository.readPosts(ChildCategory.of(category), postId);
        return createPostResponses(requestUser, posts);
    }

    private List<PostResponse> createPostResponses(final User requestUser, final List<Post> posts) {
        return posts.stream()
                .map(post -> createPostResponse(post, requestUser))
                .collect(toList());
    }

    private PostResponse createPostResponse(final Post post, final User requestUser) {
        return PostResponse.builder()
                .id(post.getId())
                .parentCategory(post.getParentCategory())
                .childCategory(post.getChildCategory())
                .authorName(post.getAuthorName())
                .title(post.getTitle())
                .tags(post.getTagNames())
                .mine(isMine(post.getAuthor(), requestUser))
                .liked(isLikedBy(post, requestUser))
                .likesCount(post.getLikeCount())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .build();
    }

    private boolean isMine(final User author, final User requestUser) {
        if (Objects.isNull(requestUser)) {
            return false;
        }
        return author.equals(requestUser);
    }

    private boolean isLikedBy(final Post post, final User requestUser) {
        if (Objects.isNull(requestUser)) {
            return false;
        }
        return post.isLikedBy(requestUser);
    }

    private Post findPostById(final Long id) {
        return postRepository.findPostById(id)
                .orElseThrow(PostNotFoundException::new);
    }

    private User findUserByUsername(final String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(UserNotFoundException::new);
    }
}