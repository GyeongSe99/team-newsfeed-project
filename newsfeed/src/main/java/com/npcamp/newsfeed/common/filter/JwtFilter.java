package com.npcamp.newsfeed.common.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.npcamp.newsfeed.common.constant.RequestAttribute;
import com.npcamp.newsfeed.common.exception.ErrorCode;
import com.npcamp.newsfeed.common.payload.ApiResponse;
import com.npcamp.newsfeed.common.security.JwtUtil;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;

@Slf4j
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

        // 유효하지 않은 토큰일 때 jwtExceptionHandler 호출
        jwtExceptionHandler(httpResponse, ErrorCode.NOT_VALID_TOKEN);

    }

    // 토큰 유효하지 않을 때 클라이언트에 JSON, ApiResponse로 응답하기 위한 예외처리 메서드
    public void jwtExceptionHandler(HttpServletResponse response, ErrorCode errorCode) {
        response.setStatus(errorCode.getStatus().value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        try {
            String json = new ObjectMapper().writeValueAsString(ApiResponse.failure(errorCode.getMsg()));
            response.getWriter().write(json);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
