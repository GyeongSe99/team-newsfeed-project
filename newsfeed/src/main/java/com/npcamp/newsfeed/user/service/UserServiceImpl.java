package com.npcamp.newsfeed.user.service;

import com.npcamp.newsfeed.common.entity.User;
import com.npcamp.newsfeed.common.exception.ErrorCode;
import com.npcamp.newsfeed.common.exception.ResourceConflictException;
import com.npcamp.newsfeed.common.exception.ResourceForbiddenException;
import com.npcamp.newsfeed.common.security.PasswordEncoder;
import com.npcamp.newsfeed.common.validator.PasswordValidator;
import com.npcamp.newsfeed.user.dto.UpdateUserResponseDto;
import com.npcamp.newsfeed.user.dto.UserResponseDto;
import com.npcamp.newsfeed.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final PasswordValidator passwordValidator;

    @Override
    @Transactional(readOnly = true)
    public UserResponseDto getUser(Long id) {
        User user = userRepository.findByIdOrElseThrow(id);
        return UserResponseDto.toDto(user);
    }

    /**
     * 회원 정보 수정
     *
     * @param id       수정할 회원의 ID
     * @param name     변경할 회원 이름 (null 가능)
     * @param email    변경할 회원 이메일 (유니크 조건 검사)
     * @param password 현재 비밀번호 (일치해야 수정 가능)
     * @return 수정된 회원 정보 응답 DTO
     * @throws ResourceForbiddenException 비밀번호가 일치하지 않을 경우 발생
     * @throws ResourceConflictException  중복된 이메일이 존재하는 경우 발생
     */
    @Override
    @Transactional
    public UpdateUserResponseDto updateUser(Long id, String name, String email, String password) {

        // 해당 Id를 갖는 유저 조회( 없다면 ResourceNotFoundException)
        User user = userRepository.findByIdOrElseThrow(id);

        // 비밀번호 일치여부 확인
        passwordValidator.verifyMatch(password, user.getPassword());

        // 유니크 이메일 중복 확인
        if (!user.getEmail().equals(email) && userRepository.existsByEmail(email)) {
            throw new ResourceConflictException(ErrorCode.DUPLICATE_EMAIL);
        }

        user.updateUser(name, email);
        User savedUser = userRepository.save(user);

        return UpdateUserResponseDto.toDto(savedUser);
    }

    /**
     * 회원 비밀번호 수정
     *
     * @param id          수정할 회원의 ID
     * @param oldPassword 기존 회원의 비밀번호
     * @param newPassword 변경할 비밀번호
     */
    @Override
    @Transactional
    public void updatePassword(Long id, String oldPassword, String newPassword) {

        // 해당 Id를 갖는 유저 조회
        User user = userRepository.findByIdOrElseThrow(id);

        // 비밀번호 일치여부 확인
        passwordValidator.verifyMatch(oldPassword, user.getPassword());

        // 기존 비밀번호와 동일한지 확인. 동일할 경우 변경할 수 없으므로 Exception 발생
        passwordValidator.verifyNotSame(newPassword, oldPassword);

        user.updatePassword(encoder.encode(newPassword));
    }

}