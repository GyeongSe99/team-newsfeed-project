package com.npcamp.newsfeed.follow.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class FollowListDto {

    private final List<FollowResponseDto> follows;

}
