package com.youn.intly.comment.presentation;

import com.youn.intly.authentication.domain.Authenticated;
import com.youn.intly.authentication.domain.user.AppUser;
import com.youn.intly.comment.application.CommentService;
import com.youn.intly.comment.dto.request.CommentRequest;
import com.youn.intly.comment.dto.request.SubCommentRequest;
import com.youn.intly.comment.dto.response.LeaveCommentResponse;
import com.youn.intly.comment.dto.response.LeaveSubCommentResponse;
import com.youn.intly.configuration.auth_interceptor_register.ForOnlyLoginUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api")
@RequiredArgsConstructor
@RestController
public class CommentController {

    private final CommentService commentService;

    @ForOnlyLoginUser
    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<LeaveCommentResponse> writeComment(
        @Authenticated AppUser user,
        @PathVariable Long postId,
        @RequestBody CommentRequest commentRequest
    ) {
        LeaveCommentResponse leaveCommentResponse =
                commentService.writeComment(user, postId, commentRequest);

        return ResponseEntity.ok(leaveCommentResponse);
    }

    @ForOnlyLoginUser
    @PostMapping("/posts/{postId}/comments/{rootParentId}")
    public ResponseEntity<LeaveSubCommentResponse> writeSubComment(
        @Authenticated AppUser user,
        @PathVariable Long postId,
        @PathVariable Long rootParentId,
        @RequestBody SubCommentRequest subCommentRequest
    ) {
        LeaveSubCommentResponse leaveSubCommentResponse =
                commentService.writeSubComment(user, postId, rootParentId, subCommentRequest);

        return ResponseEntity.ok(leaveSubCommentResponse);
    }
}