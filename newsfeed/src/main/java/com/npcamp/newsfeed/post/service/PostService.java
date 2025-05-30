package com.npcamp.newsfeed.post.service;

import com.npcamp.newsfeed.common.entity.Post;
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
    public PostResponseDto createPost(String title, String content, Long writerId) {
        Post post = Post.builder().title(title).content(content).writerId(writerId).build();
        Post saved = postRepository.save(post);
        return PostResponseDto.toDto(saved);
    }

    /**
     * READ ONE: 단일 게시글 조회
     */
    public PostResponseDto getPost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new RuntimeException("Post not found: " + id));
        return PostResponseDto.toDto(post);
    }


    /**
     * UPDATE: 게시글 수정 후 DTO로 반환
     */
    public PostResponseDto updatePost(Long id, String title, String content) {
        Post post = postRepository.findById(id).orElseThrow(() -> new RuntimeException("Post not found: " + id));
        post.setTitle(title);
        post.setContent(content);

        Post updated = postRepository.save(post);
        return PostResponseDto.toDto(updated);
    }

    /**
     * DELETE: 게시글 삭제
     */
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }

    /**
     * 게시물 페이지 조회.
     * 페이지네이션이 적용된 결과를 반환합니다.
     *
     * @param pageable size, sort, direction, page
     */
    public Page<PostResponseDto> getPostPage(Pageable pageable) {
        Page<Post> postPage = postRepository.findAll(pageable);
        return postPage.map(PostResponseDto::toDto);
    }
}