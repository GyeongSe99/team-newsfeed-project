package com.npcamp.newsfeed.follow.service;

import com.npcamp.newsfeed.common.entity.Follow;
import com.npcamp.newsfeed.follow.dto.FollowResponseDto;
import com.npcamp.newsfeed.follow.repository.FollowRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FollowService {

    private final FollowRepository followRepository;

    public FollowService(FollowRepository followRepository) {
        this.followRepository = followRepository;
    }

    // 새로운 팔로우 관계 생성
    @Transactional
    public FollowResponseDto createFollow(Long followerUserId, Long followeeUserId) {
        Follow follow = Follow.builder()
                .followerUserId(followerUserId)
                .followeeUserId(followeeUserId)
                .build();

        // DB에 저장
        Follow saved = followRepository.save(follow);

        // 저장된 엔티티 기반으로 응답 DTO 생성
        return FollowResponseDto.fromEntity(saved);
    }
}
