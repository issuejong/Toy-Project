package com.practice.board_management.domain.post;

import com.practice.board_management.domain.comment.Comment;
import com.practice.board_management.domain.users.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
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

    private int views;

    public Post(User author, String title, String content) {
        this.author = author;
        this.title = title;
        this.content = content;
        this.createTime = LocalDateTime.now();
    }

    public void viewsUp() {
        this.views++;
    }

    @OneToMany(mappedBy = "post")
    private List<Comment> comments;
}
