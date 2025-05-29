package com.npcamp.newsfeed.post.dto;

import com.npcamp.newsfeed.common.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class PostResponseDto {
    private final Long id;
    private final String title;
    private final String content;
    private final Long writerId;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public static PostResponseDto toDto(Post post) {
        return PostResponseDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .writerId(post.getWriterId())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .build();
    }
}