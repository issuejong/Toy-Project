package com.practice.board_management.domain.post;

import com.practice.board_management.domain.comment.Comment;
import com.practice.board_management.domain.users.User;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User author;

    @Column(nullable = false, length = 25)
    private String title;

    @Column(nullable = false, length = 2000)
    private String content;

    private LocalDateTime createTime;

    public Post(User author, String title, String content) {
        this.author = author;
        this.title = title;
        this.content = content;
        this.createTime = LocalDateTime.now();
    }

    public Post() {

    }

    @PrePersist
    protected void onCreate() {
        this.createTime = LocalDateTime.now();
    }

    @OneToMany(mappedBy = "post")
    private List<Comment> comments;
}
