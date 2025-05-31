package com.npcamp.newsfeed.follow.dto;

import com.npcamp.newsfeed.common.entity.Follow;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FollowResponseDto {
    private Long id;
    private Long followerUserId;
    private Long followeeUserId;

    // Entity → DTO 변환 메서드
    public static FollowResponseDto toDto(Follow follow) {
        return FollowResponseDto.builder()
                .id(follow.getId())
                .followerUserId(follow.getFollowerUserId())
                .followeeUserId(follow.getFolloweeUserId())
                .build();
    }
}