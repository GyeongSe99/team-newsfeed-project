package com.npcamp.newsfeed.post.controller;

import com.npcamp.newsfeed.comment.dto.CommentDto;
import com.npcamp.newsfeed.comment.dto.CreateCommentRequestDto;
import com.npcamp.newsfeed.comment.service.CommentService;
import com.npcamp.newsfeed.common.payload.ApiResponse;
import com.npcamp.newsfeed.post.dto.PostRequestDto;
import com.npcamp.newsfeed.post.dto.PostResponseDto;
import com.npcamp.newsfeed.post.service.PostService;
import com.npcamp.newsfeed.common.constant.RequestAttribute;
import jakarta.validation.Valid;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final CommentService commentService;

    /**
     * 생성
     */
    @PostMapping
    public ResponseEntity<ApiResponse<PostResponseDto>> createPost(@RequestBody @Valid PostRequestDto requestDto, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute(RequestAttribute.USER_ID);
        PostResponseDto responseDto = postService.createPost(userId, requestDto);
        return new ResponseEntity<>(ApiResponse.success(responseDto), HttpStatus.CREATED);
    }

    /**
     * 전체 조회
     */
    @GetMapping
    public ResponseEntity<ApiResponse<Page<PostResponseDto>>> getPostPage(@PageableDefault(size = 10, sort =
            "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<PostResponseDto> page = postService.getPostPage(pageable);
        return new ResponseEntity<>(ApiResponse.success(page), HttpStatus.OK);
    }

    /**
     * 단건 조회
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PostResponseDto>> getPost(@PathVariable Long id) {
        PostResponseDto dto = postService.getPost(id);
        return new ResponseEntity<>(ApiResponse.success(dto), HttpStatus.OK);
    }

    /**
     * 수정
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<PostResponseDto>> updatePost(@PathVariable Long id, @RequestBody @Valid PostRequestDto requestDto, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute(RequestAttribute.USER_ID);
        PostResponseDto updated = postService.updatePost(id, userId, requestDto);
        return new ResponseEntity<>(ApiResponse.success(updated), HttpStatus.OK);
    }

    /**
     * 삭제
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deletePost(@PathVariable Long id, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute(RequestAttribute.USER_ID);
        postService.deletePost(id, userId);
        return new ResponseEntity<>(ApiResponse.success(), HttpStatus.OK);
    }

    @PostMapping("/{id}/comments")
    public ResponseEntity<ApiResponse<CommentDto>> createComment(@PathVariable(name = "id") Long postId,
                                                                 @RequestBody @Valid CreateCommentRequestDto request) {
        CommentDto comment = commentService.createComment(postId, request.getContent(), request.getUserId());
        return new ResponseEntity<>(ApiResponse.success(comment), HttpStatus.CREATED);
    }

    /**
     * 게시글 하위의 댓글 페이지 조회 기능
     *
     * @param postId   게시글 아이디
     * @param pageable 페이징 정보를 담은 객체 (page = 페이지 번호, size = 한번에 가져올 양, sort = 정렬 조건, direction = 정렬 방향)
     * @return 댓글 목록을 담은 Page<CommentDto> 객체
     */
    @GetMapping("/{id}/comments")
    public ResponseEntity<ApiResponse<?>> getComments(@PathVariable(name = "id") Long postId,
                                                      @PageableDefault(size = 10, sort = "createdAt", direction =
                                                              Sort.Direction.DESC) Pageable pageable) {
        Page<CommentDto> commentPage = commentService.getCommentPage(postId, pageable);
        return new ResponseEntity<>(ApiResponse.success(commentPage), HttpStatus.OK);
    }
}