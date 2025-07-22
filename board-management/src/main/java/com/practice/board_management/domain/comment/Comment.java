package com.practice.board_management.domain.comment;

import com.practice.board_management.domain.post.Post;
import com.practice.board_management.domain.users.User;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long CommentId;

    @ManyToOne
    @JoinColumn(name = "postId", nullable = false)
    private Post post;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User author;

    @Column(nullable = false, length = 1000)
    private String content;

    private LocalDateTime time;


    public Comment(Post post, User author, String content) {
        this.post = post;
        this.author = author;
        this.content = content;
        this.time = LocalDateTime.now();
    }

    public Comment() {

    }

    @PrePersist
    protected void onCreate() {
        this.time = LocalDateTime.now();
    }
}
