package com.npcamp.newsfeed.follow.controller;

import com.npcamp.newsfeed.common.payload.ApiResponse;
import com.npcamp.newsfeed.follow.dto.FollowRequestDto;
import com.npcamp.newsfeed.follow.dto.FollowResponseDto;
import com.npcamp.newsfeed.follow.service.FollowService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/followers")
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;

    // 팔로우 생성 API 엔드포인트
    @PostMapping
    public ResponseEntity<ApiResponse<FollowResponseDto>> createFollow(@Valid @RequestBody FollowRequestDto followDto) {
        FollowResponseDto created = followService.createFollow(
                followDto.getFollowerUserId(),
                followDto.getFolloweeUserId()
        );
        return new ResponseEntity<>(ApiResponse.success(created), HttpStatus.CREATED);
    }
}
