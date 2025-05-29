package com.npcamp.newsfeed.follow.service;

import com.npcamp.newsfeed.common.entity.Follow;
import com.npcamp.newsfeed.follow.dto.FollowDto;
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
    public FollowDto createFollow(FollowDto followDto) {
        Follow follow = Follow.builder()
                .followerUserId(followDto.getFollowerUserId())
                .followeeUserId(followDto.getFolloweeUserId())
                .build();

        // DB에 저장
        Follow saved = followRepository.save(follow);

        // 저장된 엔티티 기반으로 응답 DTO 생성
        return FollowDto.builder()
                .followerUserId(saved.getFollowerUserId())
                .followeeUserId(saved.getFolloweeUserId())
                .build();
    }
}
