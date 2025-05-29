package com.npcamp.newsfeed.user.service;

import com.npcamp.newsfeed.auth.dto.UserResponseDto;

public interface UserService {

    UserResponseDto getUser(Long id);

}
