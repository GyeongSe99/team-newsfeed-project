package com.npcamp.newsfeed.post.controller;

import com.npcamp.newsfeed.post.dto.PostListDto;
import com.npcamp.newsfeed.post.dto.PostRequestDto;
import com.npcamp.newsfeed.post.dto.PostResponseDto;
import com.npcamp.newsfeed.post.service.PostService;
import com.npcamp.newsfeed.common.payload.ApiResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    /** 생성 */
    @PostMapping
    public ResponseEntity<ApiResponse<PostResponseDto>> createPost(
            @RequestBody @Valid PostRequestDto req
    ) {
        PostResponseDto dto = postService.createPost(
                req.getTitle(),
                req.getContent(),
                req.getWriterId()
        );
        return new ResponseEntity<>(ApiResponse.success(dto), HttpStatus.CREATED);
    }

    /** 전체 조회 */
    @GetMapping
    public ResponseEntity<ApiResponse<PostListDto>> getPostList() {
        PostListDto list = postService.getPostList();
        return ResponseEntity.ok(ApiResponse.success(list));
    }

    /** 단건 조회 */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PostResponseDto>> getPost(@PathVariable Long id) {
        PostResponseDto dto = postService.getPost(id);
        return ResponseEntity.ok(
                ApiResponse.success(dto)
        );
    }

    /** 수정 */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<PostResponseDto>> updatePost(
            @PathVariable Long id,
            @RequestBody @Valid PostRequestDto req
    ) {
        PostResponseDto updated = postService.updatePost(id,
                req.getTitle(),
                req.getContent());
        return ResponseEntity.ok(
                ApiResponse.success(updated));
    }

    /** 삭제 */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.ok(
                ApiResponse.success(null));
    }
}