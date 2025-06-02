package com.npcamp.newsfeed.common.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "post_like")

public class PostLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long postLikeId;

    @Column(name = "postId", nullable = false)
    private Long postId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "postId", insertable = false, unique = false)
    public Post post;

    @Column(name = "userId", nullable = false)
    public Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", insertable = false, updatable = false)
    public User user;
}