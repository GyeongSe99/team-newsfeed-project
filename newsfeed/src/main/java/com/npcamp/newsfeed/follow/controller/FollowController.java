package com.npcamp.newsfeed.follow.controller;

import com.npcamp.newsfeed.common.constant.RequestAttributeKey;
import com.npcamp.newsfeed.common.payload.ApiResponse;
import com.npcamp.newsfeed.follow.dto.FollowListDto;
import com.npcamp.newsfeed.follow.dto.FollowRequestDto;
import com.npcamp.newsfeed.follow.dto.FollowResponseDto;
import com.npcamp.newsfeed.follow.service.FollowService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/follow")
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;

    // 팔로우 생성
    @PostMapping
    public ResponseEntity<ApiResponse<FollowResponseDto>> createFollow(@RequestAttribute(RequestAttributeKey.USER_ID) Long loginUserId, @Valid @RequestBody FollowRequestDto followDto) {

        FollowResponseDto responseDto = followService.createFollow(loginUserId, followDto.getFolloweeUserId());

        return new ResponseEntity<>(ApiResponse.success(responseDto), HttpStatus.CREATED);
    }

    // 나의 팔로워 목록 조회
    @GetMapping("/me/followers")
    public ResponseEntity<ApiResponse<FollowListDto>> getFollowers(@RequestAttribute(RequestAttributeKey.USER_ID) Long userId) {
        FollowListDto followers = followService.getFollowers(userId);
        return new ResponseEntity<>(ApiResponse.success(followers), HttpStatus.OK);
    }

    // 나의 팔로잉 목록 조회
    @GetMapping("/me/followings")
    public ResponseEntity<ApiResponse<FollowListDto>> getFollowings(@RequestAttribute(RequestAttributeKey.USER_ID) Long userId) {
        FollowListDto followings = followService.getFollowees(userId);
        return new ResponseEntity<>(ApiResponse.success(followings), HttpStatus.OK);
    }

    // 단일 팔로우 관계 조회
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<FollowResponseDto>> getFollowById(@PathVariable Long id) {
        FollowResponseDto followDto = followService.getFollowById(id);
        return new ResponseEntity<>(ApiResponse.success(followDto), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<ApiResponse<Void>> deleteFollow(@RequestAttribute(RequestAttributeKey.USER_ID) Long loginUserId, @Valid @RequestBody FollowRequestDto followDto) {
        followService.deleteFollow(loginUserId, followDto.getFolloweeUserId());
        return new ResponseEntity<>(ApiResponse.success("팔로우를 취소했습니다."), HttpStatus.OK);
    }
}