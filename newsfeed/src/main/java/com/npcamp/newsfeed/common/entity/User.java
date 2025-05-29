package com.npcamp.newsfeed.common.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.util.Strings;

@Entity
@Getter
@NoArgsConstructor
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Builder
    private User(String name, String password, String email) {
        this.name = name;
        this.password = password;
        this.email = email;
    }

    public void updatePassword(String password) {
        this.password = password;
    }

    public void updateUser(String name, String email) {
        if (Strings.isNotBlank(name)) {
            this.name = name;
        }

        if (Strings.isNotBlank(email)) {
            this.email = email;
        }
    }
}