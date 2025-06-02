package com.npcamp.newsfeed.post.service;

import com.npcamp.newsfeed.common.entity.Post;
import com.npcamp.newsfeed.common.exception.ErrorCode;
import com.npcamp.newsfeed.common.exception.ResourceNotFoundException;
import com.npcamp.newsfeed.common.validator.AuthorValidator;
import com.npcamp.newsfeed.post.dto.PostResponseDto;
import com.npcamp.newsfeed.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final AuthorValidator authorValidator;

    /**
     * CREATE: 새 게시글을 저장하고 DTO로 반환한다.
     */
    @Transactional
    public PostResponseDto createPost(String title, String content, Long writerId) {

        Post post = Post.builder()
                .title(title)
                .content(content)
                .writerId(writerId)
                .build();

        Post saved = postRepository.save(post);

        return PostResponseDto.toDto(saved);
    }

    /**
     * READ ONE: 단일 게시글 조회
     */
    @Transactional(readOnly = true)
    public PostResponseDto getPost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(ErrorCode.POST_NOT_FOUND));
        return PostResponseDto.toDto(post);
    }


    /**
     * UPDATE: 게시글 수정 후 DTO로 반환
     */
    @Transactional
    public PostResponseDto updatePost(Long postId, Long userId, String title, String content) {

        // 게시글 조회
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException(ErrorCode.POST_NOT_FOUND));

        // writerId와 로그인된 userId 일치여부 확인
        authorValidator.validateOwner(post.getWriterId(), userId);

        // 값을 선택하여 수정 가능하게 하는 로직
        if (Strings.isNotBlank(title)) {
            post.setTitle(title);
        }
        if (Strings.isNotBlank(content)) {
            post.setContent(content);
        }

        // 수정된 데이터 영속화
        Post updated = postRepository.save(post);

        return PostResponseDto.toDto(updated);
    }

    /**
     * DELETE: 게시글 삭제
     */
    @Transactional
    public void deletePost(Long postId, Long userId) {

        // 게시글 조회
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException(ErrorCode.POST_NOT_FOUND));

        // writerId와 로그인된 userId 일치여부 확인
        authorValidator.validateOwner(post.getWriterId(), userId);

        // hard delete
        postRepository.deleteById(postId);

    }

    /**
     * 게시물 페이지 조회.
     * 페이지네이션이 적용된 결과를 반환합니다.
     *
     * @param pageable size, sort, direction, page
     */
    @Transactional(readOnly = true)
    public Page<PostResponseDto> getPostPage(Pageable pageable) {
        Page<Post> postPage = postRepository.findAll(pageable);
        return postPage.map(PostResponseDto::toDto);
    }
}