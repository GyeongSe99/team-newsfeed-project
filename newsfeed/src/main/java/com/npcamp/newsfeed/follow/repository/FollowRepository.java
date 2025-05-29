package com.npcamp.newsfeed.follow.repository;

import com.npcamp.newsfeed.common.entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    // 추가 쿼리 메서드 필요 시 여기에 작성
}