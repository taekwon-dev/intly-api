package com.youn.intly.comment.presentation;

import com.youn.intly.authentication.domain.Authenticated;
import com.youn.intly.authentication.domain.user.AppUser;
import com.youn.intly.comment.application.CommentFeedService;
import com.youn.intly.comment.dto.response.CommentResponse;
import com.youn.intly.comment.dto.response.SubCommentResponse;
import com.youn.intly.configuration.auth_interceptor_register.ForOnlyLoginUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class CommentFeedController {

    private final CommentFeedService commentFeedService;

    @ForOnlyLoginUser
    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<List<CommentResponse>> readComments(
        @Authenticated AppUser user,
        @PathVariable Long postId,
        @RequestParam(required = false) Long commentId
    ) {
        List<CommentResponse> commentResponses = commentFeedService.readComments(user, postId, commentId);
        return ResponseEntity.ok(commentResponses);
    }

    @ForOnlyLoginUser
    @GetMapping("/posts/{postId}/comments/{commentId}/subcomments")
    public ResponseEntity<List<SubCommentResponse>> readSubComments(
        @Authenticated AppUser user,
        @PathVariable Long postId,
        @PathVariable Long commentId,
        @RequestParam(required = false) Long subCommentId
    ) {
        List<SubCommentResponse> subCommentResponses =
                commentFeedService.readSubComments(user, postId, commentId, subCommentId);
        return ResponseEntity.ok(subCommentResponses);
    }
}