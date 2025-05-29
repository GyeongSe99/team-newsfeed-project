package com.npcamp.newsfeed.user.dto;

import com.npcamp.newsfeed.common.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class UserResponseDto {

    private final Long id;

    private final String name;

    private final String email;

    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public static UserResponseDto toDto(User user) {
        return UserResponseDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
}
