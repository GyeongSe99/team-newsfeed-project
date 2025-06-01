package com.npcamp.newsfeed.comment.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CreateCommentRequestDto {

    @NotBlank(message = "댓글 내용을 입력해주세요.")
    private String content;

    // TODO : JWT 사용한 인증 로직 적용 후 삭제
    private Long userId;

}
