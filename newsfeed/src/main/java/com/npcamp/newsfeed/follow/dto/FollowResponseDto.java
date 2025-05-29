package com.npcamp.newsfeed.follow.dto;

import com.npcamp.newsfeed.common.entity.Follow;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FollowResponseDto {

    private Long followerUserId;
    private Long followeeUserId;

    public static FollowResponseDto fromEntity(Follow follow) {
        return FollowResponseDto.builder()
                .followerUserId(follow.getFollowerUserId())
                .followeeUserId(follow.getFolloweeUserId())
                .build();
    }
}
