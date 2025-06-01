package com.npcamp.newsfeed.post.service;

import com.npcamp.newsfeed.common.entity.Post;
import com.npcamp.newsfeed.common.exception.*;
import com.npcamp.newsfeed.post.dto.PostRequestDto;
import com.npcamp.newsfeed.post.dto.PostResponseDto;
import com.npcamp.newsfeed.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class PostService {

    private final PostRepository postRepository;

    /**
     * CREATE: 새 게시글을 저장하고 DTO로 반환한다.
     */
    public PostResponseDto createPost(Long userId, PostRequestDto requestDto) {
        Post post = Post.builder()
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .writerId(userId)
                .build();
        Post saved = postRepository.save(post);
        return PostResponseDto.toDto(saved);
    }

    /**
     * READ ONE: 단일 게시글 조회
     */
    @Transactional
    public PostResponseDto getPost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.POST_NOT_FOUND));
        return PostResponseDto.toDto(post);
    }


    /**
     * UPDATE: 게시글 수정 후 DTO로 반환
     */
    public PostResponseDto updatePost(Long postId, Long userId, PostRequestDto requestDto) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException(ErrorCode.POST_NOT_FOUND));

        if (!post.getWriterId().equals(userId)) {
            throw new ResourceForbiddenException(ErrorCode.UNAUTHORIZED_USER);
        }

        post.setTitle(requestDto.getTitle());
        post.setContent(requestDto.getContent());
/*
        setUpdatedAt 는 japAuditing에서 자동으로 되는걸로 알고 추가하지 않았습니다.
*/

        Post updated = postRepository.save(post);
        return PostResponseDto.toDto(updated);
    }

    /**
     * DELETE: 게시글 삭제
     */
    public void deletePost(Long postId, Long userId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException(ErrorCode.POST_NOT_FOUND));

        if (!post.getWriterId().equals(userId)) {
            throw new ResourceForbiddenException(ErrorCode.UNAUTHORIZED_USER);        }

        postRepository.delete(post);
    }

    /**
     * 게시물 페이지 조회.
     * 페이지네이션이 적용된 결과를 반환합니다.
     *
     * @param pageable size, sort, direction, page
     */
    @Transactional
    public Page<PostResponseDto> getPostPage(Pageable pageable) {
        Page<Post> postPage = postRepository.findAll(pageable);
        return postPage.map(PostResponseDto::toDto);
    }
}