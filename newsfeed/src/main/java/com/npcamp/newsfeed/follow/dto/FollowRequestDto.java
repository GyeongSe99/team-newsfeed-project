package com.npcamp.newsfeed.follow.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FollowRequestDto {
    @NotNull(message = "팔로워 ID는 필수입니다.")
    private final Long followerUserId; // 팔로우하는 사용자 ID

    @NotNull(message = "팔로워 대상 ID는 필수입니다.")
    private final Long followeeUserId; // 팔로우 받는 사용자 ID
}
