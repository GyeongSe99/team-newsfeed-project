package com.npcamp.newsfeed.comment.service;

import com.npcamp.newsfeed.comment.dto.CommentDto;
import com.npcamp.newsfeed.comment.repository.CommentRepository;
import com.npcamp.newsfeed.common.entity.Comment;
import com.npcamp.newsfeed.common.entity.Post;
import com.npcamp.newsfeed.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    public CommentDto createComment(Long postId, String content, Long userId) {
        Post findPost = postRepository.findByIdOrElseThrow(postId);

        Comment comment = Comment.builder()
                .postId(findPost.getId())
                .content(content)
                .userId(userId)
                .build();

        Comment saved = commentRepository.save(comment);

        return CommentDto.toDto(saved);
    }

    public CommentDto getComment(Long id) {
        Comment comment = commentRepository.findByIdOrElseThrow(id);
        return CommentDto.toDto(comment);
    }
}
