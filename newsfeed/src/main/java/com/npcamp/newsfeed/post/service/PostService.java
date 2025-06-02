package com.npcamp.newsfeed.post.service;

import com.npcamp.newsfeed.common.entity.Post;
import com.npcamp.newsfeed.common.entity.PostLike;
import com.npcamp.newsfeed.common.exception.*;
import com.npcamp.newsfeed.common.validator.AuthorValidator;
import com.npcamp.newsfeed.post.dto.PostRequestDto;
import com.npcamp.newsfeed.post.dto.PostResponseDto;
import com.npcamp.newsfeed.post.repository.PostLikeRepository;
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
    private final PostLikeRepository postLikeRepository;
    private final AuthorValidator authorValidator;

    /**
     * CREATE: 새 게시글을 저장하고 DTO로 반환한다.
     */
    @Transactional
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
    @Transactional
    public PostResponseDto updatePost(Long postId, Long userId, PostRequestDto requestDto) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException(ErrorCode.POST_NOT_FOUND));

        authorValidator.validateOwner(post.getWriterId(), userId);

        post.setTitle(requestDto.getTitle());
        post.setContent(requestDto.getContent());

        Post updated = postRepository.save(post);
        return PostResponseDto.toDto(updated);
    }

    /**
     * DELETE: 게시글 삭제
     */
    @Transactional
    public void deletePost(Long postId, Long userId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException(ErrorCode.POST_NOT_FOUND));

        authorValidator.validateOwner(post.getWriterId(), userId);

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

    @Transactional
    public boolean toggleLike(Long postId, Long userId) {
        // 게시물 존재 여부
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.POST_NOT_FOUND));

        // 본인 게시글인지 검사 (본인 작성글에는 좋아요 불가)
        if (post.getWriterId().equals(userId)) {
            throw new ResourceForbiddenException(ErrorCode.CANNOT_LIKE_OWN_POST);
        }

        // 이미 좋아요가 눌러져 있는지 확인
        boolean alreadyLiked = postLikeRepository.existsByUserIdAndPostId(userId, postId);

        if (alreadyLiked) {
            // 좋아요가 이미 있으면 삭제
            postLikeRepository.deleteByUserIdAndPostId(userId, postId);
            return false; // 취소 상태
        } else {
            // 좋아요가 없으면 새로 추가
            PostLike like = PostLike.builder()
                    .postId(postId)
                    .userId(userId)
                    .build();
            postLikeRepository.save(like);
            return true; // 등록 상태
        }
    }
}