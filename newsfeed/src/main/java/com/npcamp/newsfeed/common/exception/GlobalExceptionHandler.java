package com.npcamp.newsfeed.common.exception;

import com.npcamp.newsfeed.common.payload.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    protected ResponseEntity<ApiResponse<Void>> handleResourceNotFoundException(ResourceNotFoundException e) {
        HttpStatus status = e.getErrorCode().getStatus();
        return ResponseEntity.status(status).body(ApiResponse.failure(e.getErrorCode().getMsg()));
    }

    @ExceptionHandler(ResourceConflictException.class)
    protected ResponseEntity<ApiResponse<Void>> handleResourceConflictException(ResourceConflictException e) {
        HttpStatus status = e.getErrorCode().getStatus();
        return ResponseEntity.status(status).body(ApiResponse.failure(e.getErrorCode().getMsg()));
    }

    @ExceptionHandler(ResourceForbiddenException.class)
    protected ResponseEntity<ApiResponse<Void>> handleResourceForbiddenException(ResourceForbiddenException e) {
        HttpStatus status = e.getErrorCode().getStatus();
        return ResponseEntity.status(status).body(ApiResponse.failure(e.getErrorCode().getMsg()));
    }

}
