package com.youn.intly.post.application;

import com.youn.intly.authentication.domain.user.AppUser;
import com.youn.intly.exception.post.PostNotFoundException;
import com.youn.intly.exception.user.UserNotFoundException;
import com.youn.intly.post.domain.Post;
import com.youn.intly.post.domain.category.ChildCategory;
import com.youn.intly.post.domain.category.ParentCategory;
import com.youn.intly.post.domain.content.PostContent;
import com.youn.intly.post.domain.content.PostTitle;
import com.youn.intly.post.domain.repository.PostRepository;
import com.youn.intly.post.dto.request.PostRequest;
import com.youn.intly.post.dto.response.LikePostResponse;
import com.youn.intly.post.dto.response.PostUrlResponse;
import com.youn.intly.tag.application.TagService;
import com.youn.intly.tag.domain.Tag;
import com.youn.intly.tag.dto.TagsDto;
import com.youn.intly.user.domain.User;
import com.youn.intly.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final TagService tagService;

    @Transactional
    public PostUrlResponse write(final AppUser user, final PostRequest postRequest) {
        Post post = createPost(user, postRequest);
        Post savedPost = postRepository.save(post);

        return new PostUrlResponse(savedPost.getId());
    }

    private Post createPost(final AppUser user, final PostRequest postRequest) {
        String authorName = user.getUsername();
        User author = findUserByUsername(authorName);
        List<Tag> tags = tagService.findOrCreateTags(new TagsDto(postRequest.getTags()));

        Post post = createPost(
                author,
                postRequest.getParentCategory(),
                postRequest.getChildCategory(),
                postRequest.getTitle(),
                postRequest.getContent()
        );
        post.addTags(tags);
        return post;
    }

    private Post createPost(
        final User author,
        final ParentCategory parentCategory,
        final ChildCategory childCategory,
        final String title,
        final String content
    ) {
        return Post.builder()
                .author(author)
                .parentCategory(parentCategory)
                .childCategory(childCategory)
                .title(new PostTitle(title))
                .content(new PostContent(content))
                .build();
    }

    @Transactional
    public LikePostResponse like(AppUser user, Long postId) {
        User requestUser = findUserByUsername(user.getUsername());
        Post post = findPostById(postId);
        post.like(requestUser);

        return new LikePostResponse(post.getLikeCount(), true);
    }

    @Transactional
    public LikePostResponse cancelLike(AppUser user, Long postId) {
        User requestUser = findUserByUsername(user.getUsername());
        Post post = findPostById(postId);
        post.cancelLike(requestUser);

        return new LikePostResponse(post.getLikeCount(), false);
    }

    private User findUserByUsername(final String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(UserNotFoundException::new);
    }

    private Post findPostById(final Long id) {
        return postRepository.findPostById(id)
                .orElseThrow(PostNotFoundException::new);
    }
}