package com.npcamp.newsfeed.follow.service;

import com.npcamp.newsfeed.common.entity.Follow;
import com.npcamp.newsfeed.common.exception.ErrorCode;
import com.npcamp.newsfeed.common.exception.ResourceConflictException;
import com.npcamp.newsfeed.common.exception.ResourceForbiddenException;
import com.npcamp.newsfeed.common.exception.ResourceNotFoundException;
import com.npcamp.newsfeed.follow.dto.FollowListDto;
import com.npcamp.newsfeed.follow.dto.FollowResponseDto;
import com.npcamp.newsfeed.follow.repository.FollowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.npcamp.newsfeed.common.exception.ErrorCode.ALREADY_FOLLOW;
import static com.npcamp.newsfeed.common.exception.ErrorCode.SELF_FOLLOW_NOT_ALLOWED;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;

    /**
     * 팔로우 관계를 생성하는 기능
     *
     * @param followerUserId 로그인된 유저 팔로우를 요청하는 사람의 id
     * @param followeeUserId 팔로우할 대상의 id
     * @return
     */
    @Transactional
    public FollowResponseDto createFollow(Long followerUserId, Long followeeUserId) {

        // 본인을 팔로우할 수 없음
        if (followerUserId.equals(followeeUserId)) {
            throw new ResourceForbiddenException(SELF_FOLLOW_NOT_ALLOWED);
        }

        // 이미 팔로우 관계
        if (followRepository.existsByFollowerUserIdAndFolloweeUserId(followerUserId, followeeUserId)) {
            throw new ResourceConflictException(ALREADY_FOLLOW);
        }

        Follow follow = Follow.builder()
                .followerUserId(followerUserId)
                .followeeUserId(followeeUserId)
                .build();

        Follow saved = followRepository.save(follow);

        return FollowResponseDto.toDto(saved);
    }

    // 나의 팔로워 목록 조회
    @Transactional(readOnly = true)
    public FollowListDto getFollowers(Long userId) {

        List<FollowResponseDto> follows =
                followRepository.findByFolloweeUserId(userId)
                        .stream()
                        .map(FollowResponseDto::toDto)
                        .collect(Collectors.toList());

        return new FollowListDto(follows);
    }

    // 나의 팔로잉 목록 조회
    @Transactional(readOnly = true)
    public FollowListDto getFollowees(Long userId) {

        List<FollowResponseDto> follows =
                followRepository.findByFollowerUserId(userId)
                        .stream()
                        .map(FollowResponseDto::toDto)
                        .collect(Collectors.toList());

        return new FollowListDto(follows);
    }

    // 단일 팔로우 관계 조회
    @Transactional(readOnly = true)
    public FollowResponseDto getFollowById(Long id) {
        Follow follow = followRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.FOLLOW_NOT_FOUND));
        return FollowResponseDto.toDto(follow);
    }

    @Transactional
    public void deleteFollow(Long followerUserId, Long followeeUserId) {

        Follow follow = followRepository.findByFollowerUserIdAndFolloweeUserId(followerUserId, followeeUserId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.FOLLOW_NOT_FOUND));

        followRepository.delete(follow);
    }
}