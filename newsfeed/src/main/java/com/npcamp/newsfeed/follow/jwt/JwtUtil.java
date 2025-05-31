package com.npcamp.newsfeed.follow.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;

/**
 * JWT 토큰에서 사용자 정보를 추출하는 유틸리티 클래스
 */
@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secretKey;

    // 서명 키 생성
    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    // Authorization 헤더에서 토큰을 추출하고 userId 반환
    public Long getUserIdFromToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            throw new IllegalArgumentException("유효하지 않은 Authorization 헤더입니다.");
        }

        String token = header.substring(7); // "Bearer " 제거

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

        return Long.valueOf(claims.get("userId").toString());
    }
}