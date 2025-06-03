package com.npcamp.newsfeed.follow.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FollowRequestDto {

    @NotNull(message = "팔로잉 ID는 필수입니다.")
    private Long followeeUserId; // 팔로우할 대상
}