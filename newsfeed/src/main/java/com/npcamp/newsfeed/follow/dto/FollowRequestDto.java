package com.npcamp.newsfeed.follow.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FollowRequestDto {

    @NotNull(message = "팔로워 ID는 필수입니다.")
    private Long followerUserId; // 팔로워

    @NotNull(message = "팔로잉 ID는 필수입니다.")
    private Long followeeUserId; // 팔로잉
}