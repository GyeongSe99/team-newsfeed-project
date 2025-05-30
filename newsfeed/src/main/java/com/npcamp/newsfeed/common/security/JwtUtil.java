package com.npcamp.newsfeed.common.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Slf4j(topic = "JwtUtil")
@Component
public class JwtUtil {

    public static final String BEARER_PREFIX = "Bearer ";
    // JWT 토큰의 만료 시간
    private final long TOKEN_TIME = 60 * 60 * 1000L;
    // 애플리케이션 설정 파일에서 주입받은 비밀 키
    @Value("${jwt.secret.key}")
    private String secretKey;
    // 실제 서명에 사용되는 키 객체
    private Key key;

    /**
     * 빈 초기화 메서드
     * - 애플리케이션 시작 시 비밀 키를 Base64로 디코딩하여 Key 객체를 초기화합니다.
     */
    @PostConstruct
    public void init() {
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes);
    }

    /**
     * JWT 토큰을 생성합니다.
     *
     * @param userId 사용자 id
     * @return 생성된 JWT 토큰
     */
    public String generateToken(Long userId) {
        Date now = new Date();

        return BEARER_PREFIX + Jwts.builder()
                .setSubject(userId.toString())
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + TOKEN_TIME))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * JWT 토큰에서 사용자 ID를 추출합니다.
     *
     * @param token JWT 토큰
     * @return 사용자 ID
     */
    public Long extractUserId(String token) {
        String subject = extractAllClaims(token).getSubject();
        return Long.parseLong(subject);
    }

    /**
     * JWT 토큰에서 모든 클레임을 추출합니다.
     *
     * @param token JWT 토큰
     * @return 클레임 객체
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * JWT 토큰의 유효성을 검증합니다.
     *
     * @param token 검증할 JWT 토큰
     * @return 토큰의 유효성 여부 (true: 유효함, false: 유효하지 않음)
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException | SignatureException e) {
            // 토큰 서명이 잘못되었거나, 잘못된 형식의 JWT가 전달된 경우
            log.error("잘못된 JWT 서명입니다.", e);
        } catch (ExpiredJwtException e) {
            // 토큰이 만료된 경우
            log.error("만료된 JWT 토큰입니다.", e);
        } catch (UnsupportedJwtException e) {
            // 지원되지 않는 JWT 형식이 전달된 경우
            log.error("지원하지 않는 JWT 토큰 형식입니다.", e);
        } catch (IllegalArgumentException e) {
            // JWT 클레임이 비어 있거나 잘못된 형식일 경우
            log.error("JWT 클레임이 비어 있거나 잘못되었습니다.", e);
        }
        return false;
    }
}
