package com.youn.intly.post.presentation;

import com.youn.intly.authentication.domain.Authenticated;
import com.youn.intly.authentication.domain.user.AppUser;
import com.youn.intly.configuration.auth_interceptor_register.ForOnlyLoginUser;
import com.youn.intly.post.application.PostService;
import com.youn.intly.post.dto.request.PostRequest;
import com.youn.intly.post.dto.response.LikePostResponse;
import com.youn.intly.post.dto.response.PostUrlResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RequestMapping("/api")
@RequiredArgsConstructor
@RestController
public class PostController {

    private static final String REDIRECT_URL = "/posts/%d";

    private final PostService postService;

    @ForOnlyLoginUser
    @PostMapping("/posts")
    public ResponseEntity<Void> write(
        @Authenticated AppUser user,
        @RequestBody PostRequest postRequest
    ) {
        PostUrlResponse postUrlResponse = postService.write(user, postRequest);
        String redirectURL = String.format(REDIRECT_URL, postUrlResponse.getId());

        return ResponseEntity.created(URI.create(redirectURL)).build();
    }

    @ForOnlyLoginUser
    @PutMapping("/posts/{postId}/likes")
    public ResponseEntity<LikePostResponse> like(
        @Authenticated AppUser user,
        @PathVariable Long postId
    ) {
        LikePostResponse likePostResponse = postService.like(user, postId);
        return ResponseEntity.ok(likePostResponse);
    }

    @ForOnlyLoginUser
    @DeleteMapping("/posts/{postId}/likes")
    public ResponseEntity<LikePostResponse> cancelLike(
        @Authenticated AppUser user,
        @PathVariable Long postId
    ) {
        LikePostResponse likePostResponse = postService.cancelLike(user, postId);
        return ResponseEntity.ok(likePostResponse);
    }
}