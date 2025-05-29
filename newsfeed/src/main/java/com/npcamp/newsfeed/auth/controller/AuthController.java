package com.npcamp.newsfeed.auth.controller;

import com.npcamp.newsfeed.auth.service.AuthService;
import com.npcamp.newsfeed.auth.dto.CreateUserRequestDto;
import com.npcamp.newsfeed.auth.dto.CreateUserResponseDto;
import com.npcamp.newsfeed.common.payload.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 회원가입, 회원탈퇴, 로그인, 로그아웃
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;


    @PostMapping("/signUp")
    public ResponseEntity<ApiResponse<CreateUserResponseDto>> sighUp(@Valid @RequestBody CreateUserRequestDto requestDto) {
        CreateUserResponseDto responseDto = authService.signUp(requestDto.getName(), requestDto.getEmail(), requestDto.getPassword());
        return new ResponseEntity<>(ApiResponse.success(responseDto), HttpStatus.CREATED);
    }
}
