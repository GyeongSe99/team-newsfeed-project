package com.npcamp.newsfeed.common.exception;

import lombok.Getter;

@Getter
public abstract class CustomException extends RuntimeException {
    protected ErrorCode errorCode;

    public CustomException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
