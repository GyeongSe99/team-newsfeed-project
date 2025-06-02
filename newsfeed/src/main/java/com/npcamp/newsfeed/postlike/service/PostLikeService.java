package com.npcamp.newsfeed.postlike.service;

import com.npcamp.newsfeed.common.entity.Post;
import com.npcamp.newsfeed.common.entity.PostLike;
import com.npcamp.newsfeed.common.validator.AuthorValidator;
import com.npcamp.newsfeed.post.repository.PostRepository;
import com.npcamp.newsfeed.postlike.repository.PostLikeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PostLikeService {

    private final PostLikeRepository postLikeRepository;
    private final PostRepository postRepository;
    private final AuthorValidator authorValidator;

    @Transactional
    public boolean toggleLike(Long postId, Long userId) {
        // 게시물 존재 여부
        Post post = postRepository.findByIdOrElseThrow(postId);
        // 본인 게시글인지 검사 (본인 작성글에는 좋아요 불가)
        authorValidator.validateOwner(post.getWriterId(), userId);

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
