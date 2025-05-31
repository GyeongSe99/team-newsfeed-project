package com.npcamp.newsfeed.post.repository;

import com.npcamp.newsfeed.common.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
