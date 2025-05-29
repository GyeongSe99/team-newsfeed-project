package com.npcamp.newsfeed.post.controller;

import com.npcamp.newsfeed.post.dto.PostListDto;
import com.npcamp.newsfeed.post.dto.PostRequestDto;
import com.npcamp.newsfeed.post.dto.PostResponseDto;
import com.npcamp.newsfeed.post.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    /** 생성 */
    @PostMapping
    public ResponseEntity<PostResponseDto> createPost(
            @RequestBody @Valid PostRequestDto req
    ) {
        PostResponseDto dto = postService.createPost(req);
        return ResponseEntity.status(201).body(dto);
    }

    /** 전체 조회 */
    @GetMapping
    public ResponseEntity<PostListDto> getPostList() {
        return ResponseEntity.ok(postService.getPostList());
    }

    /** 단건 조회 */
    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDto> getPost(@PathVariable Long id) {
        return ResponseEntity.ok(postService.getPost(id));
    }

    /** 수정 */
    @PutMapping("/{id}")
    public ResponseEntity<PostResponseDto> updatePost(
            @PathVariable Long id,
            @RequestBody @Valid PostRequestDto req
    ) {
        return ResponseEntity.ok(postService.updatePost(id, req));
    }

    /** 삭제 */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }
}