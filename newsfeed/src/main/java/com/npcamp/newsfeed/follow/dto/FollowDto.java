package com.npcamp.newsfeed.follow.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FollowDto {
    private final Long followerUserId;
    private final Long followeeUserId;
}
