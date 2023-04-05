package com.youn.intly.post.presentation;

import com.youn.intly.authentication.domain.Authenticated;
import com.youn.intly.authentication.domain.user.AppUser;
import com.youn.intly.configuration.auth_interceptor_register.ForLoginAndGuestUser;
import com.youn.intly.post.application.PostFeedService;
import com.youn.intly.post.dto.response.PostDetailsResponse;
import com.youn.intly.post.dto.response.PostResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api")
@RequiredArgsConstructor
@RestController
public class PostFeedController {

    private final PostFeedService postFeedService;

    @ForLoginAndGuestUser
    @GetMapping("/posts/{postId}")
    public ResponseEntity<PostDetailsResponse> readPostDetails(
        @Authenticated AppUser user,
        @PathVariable Long postId
    ) {
        PostDetailsResponse postDetailsResponse = postFeedService.readPostDetails(user, postId);
        return ResponseEntity.ok(postDetailsResponse);
    }

    @ForLoginAndGuestUser
    @GetMapping("/posts")
    public ResponseEntity<List<PostResponse>> readPosts(
        @Authenticated AppUser user,
        @RequestParam String category,
        @RequestParam(required = false) Long postId
    ) {
        System.out.println("postId = " + postId);
        List<PostResponse> postResponses = postFeedService.readPosts(user, category, postId);
        return ResponseEntity.ok(postResponses);
    }
}