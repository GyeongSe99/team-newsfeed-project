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
    private String follower;  // 팔로워
    private String followee;  // 팔로잉

    public static FollowResponseDto toDto(Follow follow, Long loginUserId) {
        String followerMsg = follow.getFolloweeUserId().equals(loginUserId)
                ? "회원님을 팔로우 중입니다."
                : "회원님을 팔로우하고 있지 않습니다.";

        String followeeMsg = follow.getFollowerUserId().equals(loginUserId)
                ? follow.getFolloweeUserId() + "를 팔로우 중입니다."
                : follow.getFolloweeUserId() + "를 팔로우하고 있지 않습니다.";

        return FollowResponseDto.builder()
                .id(follow.getId())
                .follower(followerMsg)
                .followee(followeeMsg)
                .build();
    }
}