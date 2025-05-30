package com.npcamp.newsfeed.comment.dto;

import lombok.Getter;

@Getter
public class CreateCommentRequestDto {

    private String content;

    // TODO : JWT 사용한 인증 로직 적용 후 삭제
    private Long userId;

}
