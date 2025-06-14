package com.npcamp.newsfeed.auth.service;

import com.npcamp.newsfeed.auth.dto.CreateUserResponseDto;
import com.npcamp.newsfeed.common.entity.User;
import com.npcamp.newsfeed.common.exception.ErrorCode;
import com.npcamp.newsfeed.common.exception.ResourceConflictException;
import com.npcamp.newsfeed.common.security.JwtUtil;
import com.npcamp.newsfeed.common.security.PasswordEncoder;
import com.npcamp.newsfeed.common.validator.PasswordValidator;
import com.npcamp.newsfeed.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final JwtUtil jwtUtil;
    private final PasswordValidator passwordValidator;

    @Override
    @Transactional
    public CreateUserResponseDto signUp(String name, String email, String password) {

        // 유니크 이메일 중복 확인
        if (userRepository.existsByEmail(email)) {
            throw new ResourceConflictException(ErrorCode.DUPLICATE_EMAIL);
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

    /**
     * 회원탈퇴 로직
     *
     * @param id       사용자 id
     * @param password 사용자 비밀번호
     */
    @Override
    @Transactional
    public void deleteUser(Long id, String password) {

        // 해당 Id를 갖는 유저 조회
        User user = userRepository.findByIdOrElseThrow(id);

        // 비밀번호 일치여부 확인
        passwordValidator.verifyMatch(password, user.getPassword());

        // 회원탈퇴 ( deleted = false -> true / soft delete 적용 )
        userRepository.delete(user);
    }

    /**
     * 로그인 로직 (토큰 발급)
     *
     * @param email    사용자 email
     * @param password 사용자 비밀번호
     * @return 발급된 토큰
     */
    @Override
    public String login(String email, String password) {

        // 해당 이메일 유저 조회
        User user = userRepository.findByEmailOrElseThrow(email);

        // 비밀번호 일치여부 확인
        passwordValidator.verifyMatch(password, user.getPassword());

        return jwtUtil.generateToken(user.getId()); // JWT 토큰 발급 (userId 기반)
    }

}
