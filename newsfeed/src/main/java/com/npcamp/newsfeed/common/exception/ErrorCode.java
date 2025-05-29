package com.npcamp.newsfeed.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    // 403 : FORBIDDEN Exception
    INVALID_PASSWORD(HttpStatus.FORBIDDEN, "비밀번호가 일치하지 않습니다."),

    // 404 : NOT_FOUND Exception
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 사용자를 찾을 수 없습니다."),
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 게시물을 찾을 수 없습니다."),

    // 409 : CONFLICT Exception
    DUPLICATE_EMAIL(HttpStatus.CONFLICT, "이미 사용 중인 이메일입니다."),
    SAME_PASSWORD(HttpStatus.CONFLICT, "기존 비밀번호와 동일한 비밀번호로 변경할 수 없습니다.");

    private final HttpStatus status;
    private final String msg;
}