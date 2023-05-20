package com.youn.intly.authentication.presentation;

import com.youn.intly.authentication.application.AuthService;
import com.youn.intly.authentication.dto.TokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api")
@RequiredArgsConstructor
@RestController
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(final String username) {
        TokenResponse tokenResponse = authService.createToken(username);
        return ResponseEntity.ok(tokenResponse);
    }
}