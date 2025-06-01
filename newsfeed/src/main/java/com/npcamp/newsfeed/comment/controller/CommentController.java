package com.npcamp.newsfeed.comment.controller;

import com.npcamp.newsfeed.comment.dto.CommentDto;
import com.npcamp.newsfeed.comment.service.CommentService;
import com.npcamp.newsfeed.common.payload.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
