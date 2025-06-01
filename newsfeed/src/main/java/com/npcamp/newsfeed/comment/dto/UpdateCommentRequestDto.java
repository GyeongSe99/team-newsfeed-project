package com.npcamp.newsfeed.comment.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UpdateCommentRequestDto {

    @NotBlank(message = "댓글 내용을 입력해주세요.")
    private String content;
}
