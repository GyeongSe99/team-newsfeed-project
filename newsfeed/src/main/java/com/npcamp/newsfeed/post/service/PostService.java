package com.npcamp.newsfeed.post.service;

import com.npcamp.newsfeed.common.entity.Post;
import com.npcamp.newsfeed.post.dto.PostListDto;
import com.npcamp.newsfeed.post.dto.PostRequestDto;
import com.npcamp.newsfeed.post.dto.PostResponseDto;
import com.npcamp.newsfeed.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepo;

    /**
     * CREATE: 새 게시글을 저장하고 DTO로 반환한다.
     */
    public PostResponseDto createPost(PostRequestDto req) {
        Post post = Post.builder()
                .title(req.getTitle())
                .content(req.getContent())
                .writerId(req.getWriterId())
                .build();
        Post saved = postRepo.save(post);
        return PostResponseDto.toDto(saved);
    }

    /**
     * READ ONE: 단일 게시글 조회
     */
    public PostResponseDto getPost(Long id) {
        Post post = postRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found: " + id));
        return PostResponseDto.toDto(post);
    }

    /**
     * READ LIST: 전체 게시글 목록 조회
     */
    public PostListDto getPostList() {
        var dtoList = postRepo.findAll().stream()
                .map(PostResponseDto::toDto)
                .collect(Collectors.toList());
        return new PostListDto(dtoList);
    }

    /**
     * UPDATE: 게시글 수정 후 DTO로 반환
     */
    public PostResponseDto updatePost(Long id, PostRequestDto req) {
        Post post = postRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found: " + id));
        post.setTitle(req.getTitle());
        post.setContent(req.getContent());
        // writerId는 변경하지 않는 게 일반적이니, 필요 시에만 업데이트
        Post updated = postRepo.save(post);
        return PostResponseDto.toDto(updated);
    }

    /**
     * DELETE: 게시글 삭제
     */
    public void deletePost(Long id) {
        postRepo.deleteById(id);
    }
}