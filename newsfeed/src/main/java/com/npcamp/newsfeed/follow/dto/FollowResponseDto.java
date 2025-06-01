package com.npcamp.newsfeed.follow.dto;

import com.npcamp.newsfeed.common.entity.Follow;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FollowResponseDto {

    private Long id;
    private Long followerUserId;  // 나를 팔로우하는 사람 ID (팔로워)
    private Long followeeUserId;  // 내가 팔로우하는 사람 ID (팔로잉)

    public static FollowResponseDto toDto(Follow follow) {
        return FollowResponseDto.builder()
                .id(follow.getId())
                .followerUserId(follow.getFollowerUserId())
                .followeeUserId(follow.getFolloweeUserId())
                .build();
    }
}