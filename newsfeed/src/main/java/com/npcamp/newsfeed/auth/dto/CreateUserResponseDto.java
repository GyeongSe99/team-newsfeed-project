package com.npcamp.newsfeed.auth.dto;

import com.npcamp.newsfeed.common.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class CreateUserResponseDto {

    private final Long id;

    private final String name;

    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public static CreateUserResponseDto toDto(User user) {
        return CreateUserResponseDto.builder()
                .id(user.getId())
                .name(user.getName())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
}
