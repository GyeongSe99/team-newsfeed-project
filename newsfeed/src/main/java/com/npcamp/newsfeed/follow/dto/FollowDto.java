package com.npcamp.newsfeed.follow.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FollowDto {
    private final Long followerUserId; // 팔로우하는 사용자 ID
    private final Long followeeUserId; // 팔로우 받는 사용자 ID
}
