package com.npcamp.newsfeed.common.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "follow")
public class Follow extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long followerUserId;

    private Long followeeUserId;

    @ManyToOne
    @JoinColumn(name = "followerUserId", insertable = false, updatable = false)
    private User followerUser;

    @ManyToOne
    @JoinColumn(name = "followeeUserId", insertable = false, updatable = false)
    private User followeeUser;
}
