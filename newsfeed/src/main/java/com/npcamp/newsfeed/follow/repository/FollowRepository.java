package com.npcamp.newsfeed.follow.repository;

import com.npcamp.newsfeed.common.entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {

    // 팔로워 조회 (나를 팔로우하는 사람들 조회)
    List<Follow> findByFolloweeUserId(Long followeeUserId);

    // 팔로잉 조회 (내가 팔로우하는 사람들 조회)
    List<Follow> findByFollowerUserId(Long followerUserId);

}