package com.npcamp.newsfeed.follow.controller;

import com.npcamp.newsfeed.common.entity.Follow;
import com.npcamp.newsfeed.common.payload.ApiResponse;
import com.npcamp.newsfeed.follow.dto.FollowRequestDto;
import com.npcamp.newsfeed.follow.dto.FollowResponseDto;
import com.npcamp.newsfeed.follow.jwt.JwtUtil;
import com.npcamp.newsfeed.follow.service.FollowService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/followers")
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;
    private final JwtUtil jwtUtil;

    // 팔로우 생성
    @PostMapping
    public ResponseEntity<ApiResponse<FollowResponseDto>> createFollow(@Valid @RequestBody FollowRequestDto followDto) {
        FollowResponseDto created = followService.createFollow(
                followDto.getFollowerUserId(),
                followDto.getFolloweeUserId()
        );
        return new ResponseEntity<>(ApiResponse.success(created), HttpStatus.CREATED);
    }

    // 팔로워 조회
    @GetMapping("/followers")
    public ResponseEntity<List<Follow>> getFollowers(HttpServletRequest request) {
        Long userId = jwtUtil.getUserIdFromToken(request);
        List<Follow> followers = followService.getFollowers(userId);
        return ResponseEntity.ok(followers);
    }

    // 팔로잉 조회
    @GetMapping("/following")
    public ResponseEntity<List<Follow>> getFollowings(HttpServletRequest request) {
        Long userId = jwtUtil.getUserIdFromToken(request);
        List<Follow> followings = followService.getFollowings(userId);
        return ResponseEntity.ok(followings);
    }

    // 단일 팔로우 조회
    @GetMapping("/{id}")
    public ResponseEntity<Follow> getFollowById(@PathVariable Long id) {
        Follow follow = followService.getFollowById(id);
        return ResponseEntity.ok(follow);
    }
}
