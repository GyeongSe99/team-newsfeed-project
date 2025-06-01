package com.npcamp.newsfeed.follow.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FollowRequestDto {

    @NotNull(message = "팔로워 사용자 ID는 필수입니다.")
    private Long followerUserId; // 나를 팔로우하는 사람 ID (팔로워)

    @NotNull(message = "팔로잉 사용자 ID는 필수입니다.")
    private Long followeeUserId; // 내가 팔로우하는 사람 ID (팔로잉)
}