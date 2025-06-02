package com.npcamp.newsfeed.follow.controller;

import com.npcamp.newsfeed.common.constant.RequestAttributeKey;
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
    public ResponseEntity<ApiResponse<FollowResponseDto>> createFollow(
            @RequestAttribute("USER_ID") Long followerUserId,
            @RequestBody FollowRequestDto followDto
    ) {
        FollowResponseDto created = followService.createFollow(
                followerUserId,
                followDto.getFolloweeUserId()
        );
        return new ResponseEntity<>(ApiResponse.success(created), HttpStatus.CREATED);
    }

    // 팔로워 목록 조회
    @GetMapping("/followers")
    public ResponseEntity<ApiResponse<List<Follow>>> getFollowers(@RequestAttribute(RequestAttributeKey.USER_ID) Long userId) {
        List<Follow> followers = followService.getFollowers(userId);
        return new ResponseEntity<>(ApiResponse.success(followers), HttpStatus.OK);
    }

    // 팔로잉 목록 조회
    @GetMapping("/followings")
    public ResponseEntity<ApiResponse<List<Follow>>> getFollowings(@RequestAttribute(RequestAttributeKey.USER_ID) Long userId) {
        List<Follow> followings = followService.getFollowings(userId);
        return new ResponseEntity<>(ApiResponse.success(followings), HttpStatus.OK);
    }

    // 단일 팔로우 조회
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Follow>> getFollowById(@PathVariable Long id) {
        Follow follow = followService.getFollowById(id);
        return new ResponseEntity<>(ApiResponse.success(follow), HttpStatus.OK);
    }
}