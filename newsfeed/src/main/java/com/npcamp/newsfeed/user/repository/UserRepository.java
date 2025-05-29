package com.npcamp.newsfeed.user.repository;

import com.npcamp.newsfeed.common.entity.User;
import com.npcamp.newsfeed.common.exception.ErrorCode;
import com.npcamp.newsfeed.common.exception.ResourceNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);

    default User getUserOrElseThrow(Long id) {
        return findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(ErrorCode.USER_NOT_FOUND)
                );
    }

}
