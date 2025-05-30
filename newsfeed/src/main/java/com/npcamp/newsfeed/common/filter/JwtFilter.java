package com.npcamp.newsfeed.common.filter;

import com.npcamp.newsfeed.common.constant.RequestAttribute;
import com.npcamp.newsfeed.common.security.JwtUtil;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
public class JwtFilter implements Filter {

    private static final List<String> WHITE_LIST = List.of("/api/auth/login", "/api/auth/signUp");
    private final JwtUtil jwtUtil;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String requestURI = httpRequest.getRequestURI();

        // 로그인, 회원가입 제외
        if (WHITE_LIST.contains(requestURI)) {
            chain.doFilter(request, response);
            return;
        }

        // 헤더에서 토큰 가져오기
        String token = httpRequest.getHeader("Authorization");

        // 토큰 유효성 확인 및 httpRequest에 userId 저장
        if (token != null && token.startsWith(JwtUtil.BEARER_PREFIX)) {
            token = token.substring(JwtUtil.BEARER_PREFIX.length());

            if (jwtUtil.validateToken(token)) {
                Long userId = jwtUtil.extractUserId(token);

                httpRequest.setAttribute(RequestAttribute.USER_ID, userId);

                chain.doFilter(httpRequest, response);
                return;
            }
        }

        // TODO 유효하지 않은 토큰이면 401 응답
        httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "유효하지 않은 JWT 토큰입니다.");
    }
}
