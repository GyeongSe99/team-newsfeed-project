package com.npcamp.newsfeed.post.repository;

import com.npcamp.newsfeed.common.entity.Post;
import com.npcamp.newsfeed.common.exception.ErrorCode;
import com.npcamp.newsfeed.common.exception.ResourceNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

    default Post findByIdOrElseThrow(long id) {
        return findById(id).orElseThrow(() -> new ResourceNotFoundException(ErrorCode.POST_NOT_FOUND));
    }
}
