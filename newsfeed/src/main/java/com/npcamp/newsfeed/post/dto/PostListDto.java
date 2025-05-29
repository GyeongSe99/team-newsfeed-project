package com.npcamp.newsfeed.post.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class PostListDto {
    private final List<PostResponseDto> result;
}