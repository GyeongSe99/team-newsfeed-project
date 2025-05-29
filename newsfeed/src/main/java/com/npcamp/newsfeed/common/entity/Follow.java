package com.npcamp.newsfeed.common.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "follow")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Follow extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long followerUserId;

    private Long followeeUserId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "followerUserId", insertable = false, updatable = false)
    private User followerUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "followeeUserId", insertable = false, updatable = false)
    private User followeeUser;
}
