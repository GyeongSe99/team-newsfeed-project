package com.npcamp.newsfeed.follow.service;

import com.npcamp.newsfeed.common.entity.Follow;
import com.npcamp.newsfeed.common.exception.ErrorCode;
import com.npcamp.newsfeed.common.exception.ResourceNotFoundException;
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
        return FollowResponseDto.toDto(saved);
    }

    // 전체 팔로워 조회
    @Transactional(readOnly = true)
    public List<FollowResponseDto> getFollowersByFollowee(Long followeeUserId) {
        List<Follow> follows = followRepository.findByFolloweeUserId(followeeUserId);
        return follows.stream()
                .map(FollowResponseDto::toDto)
                .collect(Collectors.toList());
    }

    // 전체 팔로잉 조회
    @Transactional(readOnly = true)
    public List<FollowResponseDto> getFolloweesByFollower(Long followerUserId) {
        List<Follow> follows = followRepository.findByFollowerUserId(followerUserId);
        return follows.stream()
                .map(FollowResponseDto::toDto)
                .collect(Collectors.toList());
    }

    // 팔로우 삭제
    @Transactional
    public void deleteFollow(Long id, Long loginUserId) {
        Follow follow = followRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.FOLLOW_NOT_FOUND));

        if (!follow.getFollowerUserId().equals(loginUserId)) {
            throw new ResourceNotFoundException(ErrorCode.FORBIDDEN_FOLLOW_DELETE);
        }

        followRepository.delete(follow);
    }
}
