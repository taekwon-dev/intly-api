package com.youn.intly.authentication.application;

import com.youn.intly.authentication.domain.user.AppUser;
import com.youn.intly.authentication.domain.user.GuestUser;
import com.youn.intly.authentication.domain.user.LoginUser;
import com.youn.intly.authentication.dto.TokenResponse;
import com.youn.intly.exception.user.UserNotFoundException;
import com.youn.intly.user.domain.User;
import com.youn.intly.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final JWTTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    public TokenResponse createToken(final String username) {
        String accessToken = jwtTokenProvider.createToken(username);
        return new TokenResponse(accessToken, username);
    }

    public boolean validateToken(final String accessToken) {
        return jwtTokenProvider.validateToken(accessToken);
    }

    public AppUser findAppUser(final String token) {
        if (Objects.isNull(token)) {
            return new GuestUser();
        }
        String username = jwtTokenProvider.getPayloadByKey(token, "username");
        User user = findUserByUsername(username);
        return new LoginUser(user.getUsername());
    }

    private User findUserByUsername(final String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(UserNotFoundException::new);
    }
}