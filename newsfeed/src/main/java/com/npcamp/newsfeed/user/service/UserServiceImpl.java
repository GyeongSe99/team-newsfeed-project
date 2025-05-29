package com.npcamp.newsfeed.user.service;

import com.npcamp.newsfeed.auth.dto.UserResponseDto;
import com.npcamp.newsfeed.common.entity.User;
import com.npcamp.newsfeed.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    @Override
    public UserResponseDto getUser(Long id) {
        User user = userRepository.getUserOrElseThrow(id);
        return UserResponseDto.toDto(user);
    }

}
