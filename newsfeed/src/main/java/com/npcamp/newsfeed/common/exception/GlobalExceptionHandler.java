package com.npcamp.newsfeed.common.exception;

import com.npcamp.newsfeed.common.payload.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // NotFound Exception
    @ExceptionHandler(ResourceNotFoundException.class)
    protected ResponseEntity<ApiResponse<Void>> handleResourceNotFoundException(ResourceNotFoundException e) {
        HttpStatus status = e.getErrorCode().getStatus();
        return ResponseEntity.status(status).body(ApiResponse.failure(e.getErrorCode().getMsg()));
    }

    // Conflict Exception
    @ExceptionHandler(ResourceConflictException.class)
    protected ResponseEntity<ApiResponse<Void>> handleResourceConflictException(ResourceConflictException e) {
        HttpStatus status = e.getErrorCode().getStatus();
        return ResponseEntity.status(status).body(ApiResponse.failure(e.getErrorCode().getMsg()));
    }

    // Forbidden Exception
    @ExceptionHandler(ResourceForbiddenException.class)
    protected ResponseEntity<ApiResponse<Void>> handleResourceForbiddenException(ResourceForbiddenException e) {
        HttpStatus status = e.getErrorCode().getStatus();
        return ResponseEntity.status(status).body(ApiResponse.failure(e.getErrorCode().getMsg()));
    }

    // MethodArgumentNotValidException Exception
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ApiResponse<Void>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {

        StringBuilder messages = new StringBuilder();

        // 디폴트 메시지 전부 출력
        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            messages.append(fieldError.getDefaultMessage());
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.failure(messages.toString()));
    }
}
