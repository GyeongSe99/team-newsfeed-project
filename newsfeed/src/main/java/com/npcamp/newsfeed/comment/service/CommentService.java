package com.npcamp.newsfeed.comment.service;

import com.npcamp.newsfeed.comment.dto.CommentDto;
import com.npcamp.newsfeed.comment.repository.CommentRepository;
import com.npcamp.newsfeed.common.entity.Comment;
import com.npcamp.newsfeed.common.entity.Post;
import com.npcamp.newsfeed.common.validator.AuthorValidator;
import com.npcamp.newsfeed.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final AuthorValidator authorValidator;

    @Transactional
    public CommentDto createComment(Long postId, String content, Long loginUserId) {
        Post findPost = postRepository.findByIdOrElseThrow(postId);

        Comment comment = Comment.builder().postId(findPost.getId()).content(content).userId(loginUserId).build();

        Comment saved = commentRepository.save(comment);

        return CommentDto.toDto(saved);
    }

    @Transactional(readOnly = true)
    public CommentDto getComment(Long id) {
        Comment comment = commentRepository.findByIdOrElseThrow(id);
        return CommentDto.toDto(comment);
    }

    @Transactional(readOnly = true)
    public Page<CommentDto> getCommentPage(Long postId, Pageable pageable) {
        Page<Comment> commentPage = commentRepository.findAllByPostId(postId, pageable);
        return commentPage.map(CommentDto::toDto);
    }

    @Transactional
    public CommentDto updateComment(Long commentId, String content, Long loginUserId) {
        Comment findComment = commentRepository.findByIdOrElseThrow(commentId);

        // 작성자 검증
        authorValidator.validateOwner(findComment.getUserId(), loginUserId);

        findComment.setContent(content);
        Comment saved = commentRepository.save(findComment);

        return CommentDto.toDto(saved);
    }

    @Transactional
    public void deleteComment(Long commentId, Long loginUserId) {
        Comment findComment = commentRepository.findByIdOrElseThrow(commentId);
        authorValidator.validateOwner(findComment.getUserId(), loginUserId);
        commentRepository.deleteById(commentId);
    }
}
