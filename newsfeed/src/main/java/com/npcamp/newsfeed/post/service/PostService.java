package com.npcamp.newsfeed.post.service;

import com.npcamp.newsfeed.common.entity.Post;
import com.npcamp.newsfeed.post.dto.PostRequestDto;
import com.npcamp.newsfeed.post.repository.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PostService {
    private final PostRepository postRepo;

    public PostService(PostRepository postRepo) {
        this.postRepo = postRepo;
    }

    public Post createPost(PostRequestDto req) {
        // 1) DTO → Entity 변환
        Post post = new Post();
        post.setTitle(req.getTitle());
        post.setContent(req.getContent());
        post.setWriterId(req.getWriterId());
        // 2) DB에 저장
        return postRepo.save(post);
    }
}
