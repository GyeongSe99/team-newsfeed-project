package com.npcamp.newsfeed.comment.repository;

import com.npcamp.newsfeed.common.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
