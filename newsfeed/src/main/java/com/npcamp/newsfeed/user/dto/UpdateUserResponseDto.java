package com.npcamp.newsfeed.user.dto;

import com.npcamp.newsfeed.common.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class UpdateUserResponseDto {

    private final String name;

    private final String email;

    private final LocalDateTime updatedAt;

    public static UpdateUserResponseDto toDto(User user) {
        return UpdateUserResponseDto.builder()
                .name(user.getName())
                .email(user.getEmail())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
}
