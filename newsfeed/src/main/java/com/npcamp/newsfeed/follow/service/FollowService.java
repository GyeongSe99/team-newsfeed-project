package com.npcamp.newsfeed.follow.service;

import com.npcamp.newsfeed.common.entity.Follow;
import com.npcamp.newsfeed.follow.dto.FollowResponseDto;
import com.npcamp.newsfeed.follow.repository.FollowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;

    // 팔로우 생성
    @Transactional
    public FollowResponseDto createFollow(Long followerUserId, Long followeeUserId) {
        Follow follow = Follow.builder()
                .followerUserId(followerUserId)
                .followeeUserId(followeeUserId)
                .build();
        Follow saved = followRepository.save(follow);
        return FollowResponseDto.toDto(saved);
    }

    // 팔로워 목록 조회
    @Transactional(readOnly = true)
    public List<Follow> getFollowers(Long userId) {
        return followRepository.findByFolloweeUserId(userId);
    }

    // 팔로잉 목록 조회
    @Transactional(readOnly = true)
    public List<Follow> getFollowings(Long userId) {
        return followRepository.findByFollowerUserId(userId);
    }

    // 단일 팔로우 조회
    @Transactional(readOnly = true)
    public Follow getFollowById(Long id) {
        return followRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("해당 팔로우 관계를 찾을 수 없습니다. (id: " + id + ")")
                );
    }
}