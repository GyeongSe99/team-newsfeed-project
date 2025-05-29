package com.npcamp.newsfeed.auth.service;

import com.npcamp.newsfeed.auth.dto.CreateUserResponseDto;

public interface AuthService {

    CreateUserResponseDto signUp(String name, String email, String password);
}
