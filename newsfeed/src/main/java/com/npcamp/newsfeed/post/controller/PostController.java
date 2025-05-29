package com.npcamp.newsfeed.post.controller;

import com.npcamp.newsfeed.common.entity.Post;
import com.npcamp.newsfeed.post.dto.PostRequestDto;
import com.npcamp.newsfeed.post.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/posts")
@Validated
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public ResponseEntity<Post> create(
            @RequestBody @Valid PostRequestDto req
    ) {
        Post created = postService.createPost(req);
        return ResponseEntity
                .status(201)
                .body(created);
    }
}
