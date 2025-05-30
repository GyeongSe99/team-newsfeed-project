package com.npcamp.newsfeed.post.service;

import com.npcamp.newsfeed.common.entity.Post;
import com.npcamp.newsfeed.post.dto.PostListDto;
import com.npcamp.newsfeed.post.dto.PostResponseDto;
import com.npcamp.newsfeed.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional
public class PostService {

    private final PostRepository postRepository;

    /**
     * CREATE: 새 게시글을 저장하고 DTO로 반환한다.
     */
    public PostResponseDto createPost(String title,
                                      String content,
                                      Long writerId) {
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
    public PostResponseDto getPost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found: " + id));
        return PostResponseDto.toDto(post);
    }

    /**
     * READ LIST: 전체 게시글 목록 조회
     */
    public PostListDto getPostList() {
        var dtoList = postRepository.findAll().stream()
                .map(PostResponseDto::toDto)
                .collect(Collectors.toList());
        return new PostListDto(dtoList);
    }

    /**
     * UPDATE: 게시글 수정 후 DTO로 반환
     */
    public PostResponseDto updatePost(Long id,
                                      String title,
                                      String content
    ) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found: " + id));
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
}