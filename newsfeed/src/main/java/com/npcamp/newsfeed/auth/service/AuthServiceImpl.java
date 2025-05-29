package com.npcamp.newsfeed.auth.service;

import com.npcamp.newsfeed.auth.dto.CreateUserResponseDto;
import com.npcamp.newsfeed.common.entity.User;
import com.npcamp.newsfeed.common.exception.ResourceConflictException;
import com.npcamp.newsfeed.common.security.PasswordEncoder;
import com.npcamp.newsfeed.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.npcamp.newsfeed.common.exception.ErrorCode.DUPLICATE_EMAIL;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    @Override
    @Transactional
    public CreateUserResponseDto signUp(String name, String email, String password) {

        // 유니크 이메일 중복 확인
        if (userRepository.existsByEmail(email)) {
            throw new ResourceConflictException(DUPLICATE_EMAIL);
        }

        // 비밀번호 암호화
        String encodedPassword = encoder.encode(password);

        User user = User.builder()
                .name(name)
                .email(email)
                .password(encodedPassword)
                .build();

        User savedUser = userRepository.save(user);

        return CreateUserResponseDto.toDto(savedUser);
    }

}
