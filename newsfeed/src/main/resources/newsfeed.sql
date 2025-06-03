-- User 테이블
CREATE TABLE user
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    email      VARCHAR(255) NOT NULL UNIQUE,
    name       VARCHAR(255) NOT NULL,
    password   VARCHAR(255) NOT NULL,
    deleted    BIT NULL,
    created_at DATETIME(6) NULL,
    updated_at DATETIME(6) NULL
);

-- Follow 테이블
CREATE TABLE follow
(
    id               BIGINT AUTO_INCREMENT PRIMARY KEY,
    follower_user_id BIGINT NULL,
    followee_user_id BIGINT NULL,
    created_at       DATETIME(6) NULL,
    updated_at       DATETIME(6) NULL,
    FOREIGN KEY (follower_user_id) REFERENCES user (id) ON DELETE CASCADE,
    FOREIGN KEY (followee_user_id) REFERENCES user (id) ON DELETE CASCADE
);

-- Post 테이블
CREATE TABLE post
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    title      VARCHAR(255) NOT NULL,
    content    LONGTEXT     NOT NULL,
    writer_id  BIGINT NULL,
    created_at DATETIME(6) NULL,
    updated_at DATETIME(6) NULL,
    FOREIGN KEY (writer_id) REFERENCES user (id) ON DELETE CASCADE
);

-- Comment 테이블
CREATE TABLE comment
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    content    VARCHAR(255) NOT NULL,
    user_id    BIGINT NULL,
    post_id    BIGINT NULL,
    created_at DATETIME(6) NULL,
    updated_at DATETIME(6) NULL,
    FOREIGN KEY (user_id) REFERENCES user (id) ON DELETE CASCADE,
    FOREIGN KEY (post_id) REFERENCES post (id) ON DELETE CASCADE
);

-- Post Like 테이블
CREATE TABLE post_like
(
    post_like_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id      BIGINT NULL,
    post_id      BIGINT NULL,
    FOREIGN KEY (user_id) REFERENCES user (id) ON DELETE CASCADE,
    FOREIGN KEY (post_id) REFERENCES post (id) ON DELETE CASCADE
);
