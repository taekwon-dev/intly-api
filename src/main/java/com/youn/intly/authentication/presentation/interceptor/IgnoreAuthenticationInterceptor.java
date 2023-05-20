package com.youn.intly.authentication.presentation.interceptor;

import com.youn.intly.authentication.application.AuthService;
import com.youn.intly.authentication.infrastructure.AuthHeader;
import com.youn.intly.authentication.infrastructure.AuthorizationExtractor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
public class IgnoreAuthenticationInterceptor implements HandlerInterceptor {

    private final AuthService authService;

    @Override
    public boolean preHandle(
        HttpServletRequest request,
        HttpServletResponse response,
        Object handler
    ) throws Exception {
        String token = AuthorizationExtractor.extract(request);
        if (authService.validateToken(token)) {
            request.setAttribute(AuthHeader.AUTHENTICATION, token);
        }
        return true;
    }
}