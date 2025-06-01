package com.npcamp.newsfeed.comment.dto;

import com.npcamp.newsfeed.common.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.LocalDateTime;

@AllArgsConstructor
@Builder
public class CommentDto {

    private final Long id;
    private final String content;
    private final Long userId;
    private final Long postId;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public static CommentDto toDto(Comment comment) {
        return CommentDto.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .userId(comment.getUserId())
                .postId(comment.getPostId())
                .createdAt(comment.getCreatedAt())
                .updatedAt(comment.getUpdatedAt())
                .build();
    }
}
