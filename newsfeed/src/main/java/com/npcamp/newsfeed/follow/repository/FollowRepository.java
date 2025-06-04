package com.npcamp.newsfeed.follow.repository;

import com.npcamp.newsfeed.common.entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {

    // 팔로워 조회
    List<Follow> findByFolloweeUserId(Long followeeUserId);

    // 팔로잉 조회
    List<Follow> findByFollowerUserId(Long followerUserId);

    boolean existsByFollowerUserIdAndFolloweeUserId(Long followerUserId, Long followeeUserId);

    Optional<Follow> findByFollowerUserIdAndFolloweeUserId(Long followerUserId, Long followeeUserId);
}
