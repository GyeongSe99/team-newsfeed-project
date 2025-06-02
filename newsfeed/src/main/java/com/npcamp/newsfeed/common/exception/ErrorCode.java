package com.npcamp.newsfeed.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    // 401 : UNAUTHORIZED Exception
    NOT_VALID_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 JWT 토큰입니다."), // 필터에서 토큰 유효성 검증에 사용
    LOGIN_REQUIRED(HttpStatus.UNAUTHORIZED, "로그인 해주세요!"), // 로그인하지 않은 사용자가 접근 시

    // 403 : FORBIDDEN Exception
    INVALID_PASSWORD(HttpStatus.FORBIDDEN, "비밀번호가 일치하지 않습니다."),
    NOT_RESOURCE_OWNER(HttpStatus.FORBIDDEN, "해당 리소스 소유자가 아닙니다."),

    // 404 : NOT_FOUND Exception
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 사용자를 찾을 수 없습니다."),
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 게시물을 찾을 수 없습니다."),
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 댓글을 찾을 수 없습니다."),

    // 409 : CONFLICT Exception
    DUPLICATE_EMAIL(HttpStatus.CONFLICT, "이미 사용 중인 이메일입니다."),
    REUSED_PASSWORD(HttpStatus.CONFLICT, "기존 비밀번호와 동일한 비밀번호로 변경할 수 없습니다.")
    ;

    private final HttpStatus status;
    private final String msg;
}