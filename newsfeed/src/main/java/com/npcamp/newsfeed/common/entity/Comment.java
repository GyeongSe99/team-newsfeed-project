package com.npcamp.newsfeed.common.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "comment")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    private Long postId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "postId", insertable = false, updatable = false)
    private Post post;

    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", insertable = false, updatable = false)
    private User user;

    public void setContent(String content) {
        this.content = content;
    }
}
