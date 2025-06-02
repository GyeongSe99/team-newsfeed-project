package com.npcamp.newsfeed.user.controller;

import com.npcamp.newsfeed.common.constant.RequestAttributeKey;
import com.npcamp.newsfeed.common.payload.ApiResponse;
import com.npcamp.newsfeed.user.dto.UpdatePasswordRequestDto;
import com.npcamp.newsfeed.user.dto.UpdateUserRequestDto;
import com.npcamp.newsfeed.user.dto.UpdateUserResponseDto;
import com.npcamp.newsfeed.user.dto.UserResponseDto;
import com.npcamp.newsfeed.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 회원 관련 기능을 담당하는 컨트롤러 클래스.
 * - 회원 조회(id)
 * - 회원 정보 수정
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponseDto>> getUser(@PathVariable Long id) {
        UserResponseDto responseDto = userService.getUser(id);
        return new ResponseEntity<>(ApiResponse.success(responseDto), HttpStatus.OK);
    }

    @PatchMapping("/me")
    public ResponseEntity<ApiResponse<UpdateUserResponseDto>> updateUser(@RequestAttribute(RequestAttributeKey.USER_ID) Long loginUserId,
                                                                         @Valid @RequestBody UpdateUserRequestDto requestDto) {

        UpdateUserResponseDto responseDto = userService.updateUser(
                loginUserId,
                requestDto.getName(),
                requestDto.getEmail(),
                requestDto.getPassword()
        );

        return new ResponseEntity<>(ApiResponse.success(responseDto), HttpStatus.OK);
    }

    @PatchMapping("/me/password")
    public ResponseEntity<ApiResponse<Void>> updatePassword(@RequestAttribute(RequestAttributeKey.USER_ID) Long id,
                                                            @Valid @RequestBody UpdatePasswordRequestDto requestDto) {
        userService.updatePassword(id, requestDto.getOldPassword(), requestDto.getNewPassword());
        return new ResponseEntity<>(ApiResponse.success("비밀번호 변경이 완료되었습니다."), HttpStatus.OK);
    }
}