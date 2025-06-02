package com.npcamp.newsfeed.postlike.repository;

import com.npcamp.newsfeed.common.entity.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    // 해당 userId, postId 조합이 이미 존재하는지 확인
    boolean existsByUserIdAndPostId(Long userId, Long postId);

    // 좋아요 취소를 위해 물리 삭제
    void deleteByUserIdAndPostId(Long userId, Long postId);
}