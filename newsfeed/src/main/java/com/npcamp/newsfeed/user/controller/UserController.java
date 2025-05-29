package com.npcamp.newsfeed.user.controller;

import com.npcamp.newsfeed.auth.dto.UserResponseDto;
import com.npcamp.newsfeed.common.payload.ApiResponse;
import com.npcamp.newsfeed.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
