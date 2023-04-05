package com.youn.intly.comment.application;

import com.youn.intly.authentication.domain.user.AppUser;
import com.youn.intly.comment.domain.Comment;
import com.youn.intly.comment.domain.repository.CommentFeedRepository;
import com.youn.intly.comment.domain.repository.CommentRepository;
import com.youn.intly.comment.dto.response.CommentResponse;
import com.youn.intly.comment.dto.response.SubCommentResponse;
import com.youn.intly.exception.comment.CommentNotFoundException;
import com.youn.intly.exception.post.PostNotFoundException;
import com.youn.intly.exception.user.UserNotFoundException;
import com.youn.intly.post.domain.Post;
import com.youn.intly.post.domain.repository.PostRepository;
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
public class CommentFeedService {

    private final CommentFeedRepository commentFeedRepository;
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public List<CommentResponse> readComments(
        final AppUser user,
        final Long postId,
        final Long commentId
    ) {
        User requestUser = findUserByUsername(user.getUsername());
        Post post = findPostById(postId);

        List<Comment> comments = commentFeedRepository.readComments(post, commentId);
        return createCommentResponses(requestUser, comments);
    }

    private List<CommentResponse> createCommentResponses(final User requestUser, final List<Comment> comments) {
        return comments.stream()
                .map(comment -> createCommentResponse(requestUser, comment))
                .collect(toList());
    }

    private CommentResponse createCommentResponse(final User requestUser, final Comment comment) {
        return CommentResponse.builder()
                .id(comment.getId())
                .username(comment.getUsername())
                .content(comment.getContent())
                .mine(isMine(comment.getUser(), requestUser))
                .createdAt(comment.getCreatedAt())
                .updatedAt(comment.getUpdatedAt())
                .build();
    }

    public List<SubCommentResponse> readSubComments(
        final AppUser user,
        final Long postId,
        final Long commentId,
        final Long subCommentId
    ) {
        User requestUser = findUserByUsername(user.getUsername());
        Post post = findPostById(postId);
        Comment comment = findCommentById(commentId);

        List<Comment> subComments = commentFeedRepository.readSubComments(post, comment, subCommentId);
        return createSubCommentResponses(requestUser, subComments);
    }

    private List<SubCommentResponse> createSubCommentResponses(
        final User requestUser,
        final List<Comment> subComments
    ) {
        return subComments.stream()
                .map(subComment -> createSubCommentResponse(requestUser, subComment))
                .collect(toList());
    }

    private SubCommentResponse createSubCommentResponse(final User requestUser, final Comment subComment) {
        return SubCommentResponse.builder()
                .id(subComment.getId())
                .parentUsername(subComment.getParentUsername())
                .username(subComment.getUsername())
                .content(subComment.getContent())
                .mine(isMine(subComment.getUser(), requestUser))
                .createdAt(subComment.getCreatedAt())
                .updatedAt(subComment.getUpdatedAt())
                .build();
    }

    private boolean isMine(final User author, final User requestUser) {
        if (Objects.isNull(requestUser)) {
            return false;
        }
        return author.equals(requestUser);
    }

    private Post findPostById(final Long postId) {
        return postRepository.findPostById(postId)
                .orElseThrow(PostNotFoundException::new);
    }

    private User findUserByUsername(final String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(UserNotFoundException::new);
    }

    private Comment findCommentById(final Long commentId) {
        return commentRepository.findCommentById(commentId)
                .orElseThrow(CommentNotFoundException::new);
    }
}