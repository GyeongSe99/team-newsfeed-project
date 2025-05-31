package com.npcamp.newsfeed.user.service;

import com.npcamp.newsfeed.user.dto.UpdateUserResponseDto;
import com.npcamp.newsfeed.user.dto.UserResponseDto;

public interface UserService {

    UserResponseDto getUser(Long id);

    UpdateUserResponseDto updateUser(Long id, String name, String email, String password);

    void updatePassword(Long id, String oldPassword, String newPassword);
}