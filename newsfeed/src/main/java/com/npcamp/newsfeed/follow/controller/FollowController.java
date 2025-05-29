package com.npcamp.newsfeed.follow.controller;

import com.npcamp.newsfeed.follow.dto.FollowDto;
import com.npcamp.newsfeed.follow.service.FollowService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/followers")
public class FollowController {

    private final FollowService followService;

    public FollowController(FollowService followService) {
        this.followService = followService;
    }

    // 팔로우 생성 API 엔드포인트
    @PostMapping
    public ResponseEntity<FollowDto> createFollow(@RequestBody FollowDto followDto) {
        FollowDto createdFollow = followService.createFollow(followDto);
        return new ResponseEntity<>(createdFollow, HttpStatus.CREATED);
    }

}
