package com.practice.board_management.domain.users;

import com.practice.board_management.domain.comment.Comment;
import com.practice.board_management.domain.post.Post;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;

@Getter
@Entity
@Table(name = "`user`")
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false, length = 20)
    private String password;

    @Column(nullable = false, length = 20)
    private String nickname;

    @Column(nullable = false, length = 255, unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private Role role;

    public User() {

    }

    // Role Enum
    public enum Role {
        USER, ADMIN
    }

    public User(String password, String nickname, String email, Role role) {
        this.password = password;
        this.nickname = nickname;
        this.email = email;
        this.role = role;
    }

    @OneToMany(mappedBy = "author")
    private List<Post> posts;

    @OneToMany(mappedBy = "author")
    private List<Comment> comments;
}
