package com.npcamp.newsfeed.follow.service;

import com.npcamp.newsfeed.common.entity.Follow;
import com.npcamp.newsfeed.common.exception.ErrorCode;
import com.npcamp.newsfeed.common.exception.ResourceNotFoundException;
import com.npcamp.newsfeed.follow.dto.FollowRequestDto;
import com.npcamp.newsfeed.follow.dto.FollowResponseDto;
import com.npcamp.newsfeed.follow.repository.FollowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;

    // 팔로우 생성
    @Transactional
    public FollowResponseDto createFollow(Long followerUserId, FollowRequestDto followDto) {
        Long followeeUserId = followDto.getFolloweeUserId(); // DTO에서 추출
        Follow follow = Follow.builder()
                .followerUserId(followerUserId)
                .followeeUserId(followeeUserId)
                .build();
        Follow saved = followRepository.save(follow);
        return FollowResponseDto.toDto(saved, followerUserId); // 로그인한 사용자 기준 메시지
    }

    // 팔로워 목록 조회
    @Transactional(readOnly = true)
    public List<FollowResponseDto> getFollowers(Long userId) {
        List<Follow> follows = followRepository.findByFolloweeUserId(userId);
        return follows.stream()
                .map(follow -> FollowResponseDto.toDto(follow, userId))
                .collect(Collectors.toList());
    }

    // 팔로잉 목록 조회
    @Transactional(readOnly = true)
    public List<FollowResponseDto> getFollowees(Long userId) {
        List<Follow> follows = followRepository.findByFollowerUserId(userId);
        return follows.stream()
                .map(follow -> FollowResponseDto.toDto(follow, userId))
                .collect(Collectors.toList());
    }

    // 단일 팔로우 조회
    @Transactional(readOnly = true)
    public FollowResponseDto getFollowById(Long id, Long loginUserId) {
        Follow follow = followRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.FOLLOW_NOT_FOUND));
        return FollowResponseDto.toDto(follow, loginUserId);
    }
}