package com.npcamp.newsfeed.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends CustomException {

    public ResourceNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
