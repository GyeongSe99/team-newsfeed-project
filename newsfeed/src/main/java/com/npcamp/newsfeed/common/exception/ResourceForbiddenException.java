package com.npcamp.newsfeed.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(HttpStatus.FORBIDDEN)
public class ResourceForbiddenException extends CustomException {

    public ResourceForbiddenException(ErrorCode errorCode) {
        super(errorCode);
    }
}
