package com.npcamp.newsfeed.follow.controller;

import com.npcamp.newsfeed.common.entity.Follow;
import com.npcamp.newsfeed.common.payload.ApiResponse;
import com.npcamp.newsfeed.follow.dto.FollowRequestDto;
import com.npcamp.newsfeed.follow.dto.FollowResponseDto;
import com.npcamp.newsfeed.follow.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/follow")
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;

    // 팔로우 생성
    @PostMapping
    public ResponseEntity<ApiResponse<FollowResponseDto>> createFollow(@RequestBody FollowRequestDto followDto) {
        FollowResponseDto created = followService.createFollow(
                followDto.getFollowerUserId(),
                followDto.getFolloweeUserId()
        );
        return new ResponseEntity<>(ApiResponse.success(created), HttpStatus.CREATED);
    }

    // 팔로워 조회
    @GetMapping("/followers/{userId}")
    public ResponseEntity<ApiResponse<List<Follow>>> getFollowers(@PathVariable Long userId) {
        List<Follow> followers = followService.getFollowers(userId);
        return ResponseEntity.ok(ApiResponse.success(followers));
    }

    // 팔로잉 조회
    @GetMapping("/followings/{userId}")
    public ResponseEntity<ApiResponse<List<Follow>>> getFollowings(@PathVariable Long userId) {
        List<Follow> followings = followService.getFollowings(userId);
        return ResponseEntity.ok(ApiResponse.success(followings));
    }

    // 단일 팔로우 조회
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Follow>> getFollowById(@PathVariable Long id) {
        Follow follow = followService.getFollowById(id);
        return ResponseEntity.ok(ApiResponse.success(follow));
    }
}