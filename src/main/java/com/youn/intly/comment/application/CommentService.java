package com.youn.intly.comment.application;

import com.youn.intly.authentication.domain.user.AppUser;
import com.youn.intly.comment.domain.Comment;
import com.youn.intly.comment.domain.repository.CommentRepository;
import com.youn.intly.comment.dto.request.CommentRequest;
import com.youn.intly.comment.dto.request.SubCommentRequest;
import com.youn.intly.comment.dto.response.LeaveCommentResponse;
import com.youn.intly.comment.dto.response.LeaveSubCommentResponse;
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

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public LeaveCommentResponse writeComment(
        final AppUser user,
        final Long postId,
        final CommentRequest commentRequest
    ) {
        User requestUser = findUserByUsername(user.getUsername());
        Post post = findPostById(postId);

        Comment comment = Comment.builder()
                .post(post)
                .user(requestUser)
                .content(commentRequest.getContent())
                .build();
        Comment savedComment = commentRepository.save(comment);

        return createLeaveCommentResponse(savedComment);
    }

    private LeaveCommentResponse createLeaveCommentResponse(final Comment comment) {
        return LeaveCommentResponse.builder()
                .id(comment.getId())
                .username(comment.getUsername())
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .updatedAt(comment.getUpdatedAt())
                .build();
    }

    @Transactional
    public LeaveSubCommentResponse writeSubComment(
        final AppUser user,
        final Long postId,
        final Long rootParentId,
        final SubCommentRequest subCommentRequest
    ) {
        User requestUser = findUserByUsername(user.getUsername());
        Post post = findPostById(postId);
        Comment rootParent = findCommentById(rootParentId);
        Comment parent = findCommentById(subCommentRequest.getParentId());

        Comment comment = Comment.builder()
                .post(post)
                .rootParent(rootParent)
                .parent(parent)
                .user(requestUser)
                .content(subCommentRequest.getContent())
                .build();

        Comment savedComment = commentRepository.save(comment);

        return createLeaveSubCommentResponse(savedComment);
    }

    private LeaveSubCommentResponse createLeaveSubCommentResponse(final Comment subComment) {
        return LeaveSubCommentResponse.builder()
                .id(subComment.getId())
                .parentUsername(findParentCommentAuthorName(subComment))
                .username(subComment.getUsername())
                .content(subComment.getContent())
                .createdAt(subComment.getCreatedAt())
                .updatedAt(subComment.getUpdatedAt())
                .build();
    }

    private String findParentCommentAuthorName(Comment subComment) {
        Comment parent = findCommentById(subComment.getParentId());
        return parent.getUsername();
    }

    private User findUserByUsername(final String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(UserNotFoundException::new);
    }

    private Post findPostById(final Long postId) {
        return postRepository.findPostById(postId)
                .orElseThrow(PostNotFoundException::new);
    }

    private Comment findCommentById(final Long commentId) {
        return commentRepository.findCommentById(commentId)
                .orElseThrow(CommentNotFoundException::new);
    }
}