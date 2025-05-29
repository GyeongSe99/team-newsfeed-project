package com.npcamp.newsfeed.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(HttpStatus.CONFLICT)
public class ResourceConflictException extends CustomException {

    public ResourceConflictException(ErrorCode errorCode) {
        super(errorCode);
    }
}
