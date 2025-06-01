package com.npcamp.newsfeed.comment.controller;

import com.npcamp.newsfeed.comment.dto.CommentDto;
import com.npcamp.newsfeed.comment.dto.UpdateCommentRequestDto;
import com.npcamp.newsfeed.comment.service.CommentService;
import com.npcamp.newsfeed.common.constant.RequestAttributeKey;
import com.npcamp.newsfeed.common.payload.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CommentDto>> getComment(@PathVariable Long id) {
        CommentDto commentDto = commentService.getComment(id);
        return new ResponseEntity<>(ApiResponse.success(commentDto), HttpStatus.OK);
    }

    @PatchMapping("/{commentId}")
    public ResponseEntity<ApiResponse<CommentDto>> updateComment(@PathVariable Long commentId,
                                                                 @RequestBody @Valid UpdateCommentRequestDto requestDto,
                                                                 @RequestAttribute(RequestAttributeKey.USER_ID) Long loginUserId) {
        CommentDto saved = commentService.updateComment(commentId, requestDto.getContent(), loginUserId);
        return new ResponseEntity<>(ApiResponse.success(saved), HttpStatus.OK);
    }
}
