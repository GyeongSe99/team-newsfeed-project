package com.npcamp.newsfeed.user.repository;

import com.npcamp.newsfeed.common.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);

}
