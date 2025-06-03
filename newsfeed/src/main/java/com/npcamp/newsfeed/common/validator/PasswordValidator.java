package com.npcamp.newsfeed.common.validator;

import com.npcamp.newsfeed.common.exception.ErrorCode;
import com.npcamp.newsfeed.common.exception.ResourceConflictException;
import com.npcamp.newsfeed.common.exception.ResourceForbiddenException;
import com.npcamp.newsfeed.common.security.PasswordEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PasswordValidator {
    private final PasswordEncoder encoder;

    /**
     * 입력받은 비밀번호와 기존의 비밀번호의 일치여부에 따른 예외 처리
     *
     * @param inputPassword 입력받은 비밀번호
     * @param originalPassword 기존의 비밀번호
     */
    public void verifyMatch(String inputPassword, String originalPassword) {
        if (!encoder.matches(inputPassword, originalPassword)) {
            throw new ResourceForbiddenException(ErrorCode.INVALID_PASSWORD);
        }
    }

    public void verifyNotSame(String inputPassword, String originalPassword) {
        if (inputPassword.equals(originalPassword)) {
            throw new ResourceConflictException(ErrorCode.REUSED_PASSWORD);
        }
    }

}
