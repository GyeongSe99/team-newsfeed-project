package com.npcamp.newsfeed.user.service;

import com.npcamp.newsfeed.user.dto.UserResponseDto;
import com.npcamp.newsfeed.common.entity.User;
import com.npcamp.newsfeed.common.exception.ErrorCode;
import com.npcamp.newsfeed.common.exception.ResourceConflictException;
import com.npcamp.newsfeed.common.exception.ResourceForbiddenException;
import com.npcamp.newsfeed.common.security.PasswordEncoder;
import com.npcamp.newsfeed.user.dto.UpdateUserResponseDto;
import com.npcamp.newsfeed.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    @Override
    public UserResponseDto getUser(Long id) {
        User user = userRepository.getUserOrElseThrow(id);
        return UserResponseDto.toDto(user);
    }

    @Override
    @Transactional
    public UpdateUserResponseDto updateUser(Long id, String name, String email, String password) {

        User user = userRepository.getUserOrElseThrow(id);

        // 비밀번호 일치여부 확인
        if (!encoder.matches(password, user.getPassword())) {
            throw new ResourceForbiddenException(ErrorCode.INVALID_PASSWORD);
        }

        // 유니크 이메일 중복 확인
        if (userRepository.existsByEmail(email)) {
            throw new ResourceConflictException(ErrorCode.DUPLICATE_EMAIL);
        }

        user.updateUser(name, email);

        return UpdateUserResponseDto.toDto(user);
    }

}
