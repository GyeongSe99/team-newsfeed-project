package com.npcamp.newsfeed.follow.controller;

import com.npcamp.newsfeed.common.constant.RequestAttributeKey;
import com.npcamp.newsfeed.common.payload.ApiResponse;
import com.npcamp.newsfeed.follow.dto.FollowRequestDto;
import com.npcamp.newsfeed.follow.dto.FollowResponseDto;
import com.npcamp.newsfeed.follow.service.FollowService;
import jakarta.validation.Valid;
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
            @RequestAttribute(RequestAttributeKey.USER_ID) Long followerUserId,
            @Valid @RequestBody FollowRequestDto followDto) {

        FollowResponseDto responseDto = followService.createFollow(followerUserId, followDto);
        return new ResponseEntity<>(ApiResponse.success(responseDto), HttpStatus.CREATED);
    }

    // 팔로워 목록 조회
    @GetMapping("/followers")
    public ResponseEntity<ApiResponse<List<FollowResponseDto>>> getFollowers(@RequestAttribute(RequestAttributeKey.USER_ID) Long userId) {
        List<FollowResponseDto> followers = followService.getFollowers(userId);
        return new ResponseEntity<>(ApiResponse.success(followers), HttpStatus.OK);
    }

    // 팔로잉 목록 조회
    @GetMapping("/followees")
    public ResponseEntity<ApiResponse<List<FollowResponseDto>>> getFollowees(@RequestAttribute(RequestAttributeKey.USER_ID) Long userId) {
        List<FollowResponseDto> followings = followService.getFollowees(userId);
        return new ResponseEntity<>(ApiResponse.success(followings), HttpStatus.OK);
    }

    // 단일 팔로우 조회
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<FollowResponseDto>> getFollowById(
            @PathVariable Long id,
            @RequestAttribute(RequestAttributeKey.USER_ID) Long loginUserId) {

        FollowResponseDto followDto = followService.getFollowById(id, loginUserId);
        return new ResponseEntity<>(ApiResponse.success(followDto), HttpStatus.OK);
    }

    // 팔로우 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteFollow(
            @PathVariable Long id,
            @RequestAttribute(RequestAttributeKey.USER_ID) Long loginUserId) {

        followService.deleteFollow(id, loginUserId);
        return new ResponseEntity<>(ApiResponse.success(null), HttpStatus.NO_CONTENT);
    }
}