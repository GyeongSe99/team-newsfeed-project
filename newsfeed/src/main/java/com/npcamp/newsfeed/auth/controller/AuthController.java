package com.npcamp.newsfeed.auth.controller;

import com.npcamp.newsfeed.auth.service.AuthService;
import com.npcamp.newsfeed.auth.dto.CreateUserRequestDto;
import com.npcamp.newsfeed.auth.dto.CreateUserResponseDto;
import com.npcamp.newsfeed.common.payload.ApiResponse;
import com.npcamp.newsfeed.auth.dto.DeleteUserRequestDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 회원 인증 관련 기능을 제공하는 컨트롤러 클래스.
 * - 회원 가입
 * - 회원 탈퇴
 * - 로그인
 * - 로그아웃
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


    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteUser(
            @PathVariable Long id,
            @RequestBody DeleteUserRequestDto requestDto
    ) {
        authService.deleteUser(id, requestDto.getPassword());
        return new ResponseEntity<>(ApiResponse.success("회원탈퇴가 완료되었습니다."), HttpStatus.OK);
    }
}
