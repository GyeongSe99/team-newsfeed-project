package com.npcamp.newsfeed.follow.controller;

import com.npcamp.newsfeed.common.constant.RequestAttributeKey;
import com.npcamp.newsfeed.common.payload.ApiResponse;
import com.npcamp.newsfeed.follow.dto.FollowResponseDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import com.npcamp.newsfeed.follow.dto.FollowRequestDto;
import com.npcamp.newsfeed.follow.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/followers")
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;

    // 팔로우 생성 API
    @PostMapping
    public ResponseEntity<ApiResponse<FollowResponseDto>> createFollow(@Valid @RequestBody FollowRequestDto followDto) {
        FollowResponseDto created = followService.createFollow(
                followDto.getFollowerUserId(),
                followDto.getFolloweeUserId()
        );
        return new ResponseEntity<>(ApiResponse.success(created), HttpStatus.CREATED);
    }

    // 전체 팔로워 조회
    @GetMapping("/followees/{followerId}")
    public ResponseEntity<ApiResponse<List<FollowResponseDto>>> getFollowees(@PathVariable Long followerId) {
        List<FollowResponseDto> followees = followService.getFollowersByFollowee(followerId);
        return ResponseEntity.ok(ApiResponse.success(followees));
    }

    // 전체 팔로잉 조회
    @GetMapping("/followers/{followeeId}")
    public ResponseEntity<ApiResponse<List<FollowResponseDto>>> getFollowers(@PathVariable Long followeeId) {
        List<FollowResponseDto> followers = followService.getFolloweesByFollower(followeeId);
        return ResponseEntity.ok(ApiResponse.success(followers));
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
