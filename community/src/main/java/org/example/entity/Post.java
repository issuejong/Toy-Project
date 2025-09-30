package org.example.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.Getter;
import org.springframework.util.StringUtils;

import java.util.List;

@Getter
@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 30, unique = true)
    @NotNull
    @NotBlank
    private String title;

    @Column(length = 1000)
    @NotBlank
    private String content;

    @Enumerated(EnumType.STRING)
    private Tag tag;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @NotBlank
    private User user;

    @OneToMany(mappedBy = "post")
    private List<Comment> comments;

    public Post() {

    }

    public Post(String title, String content, Tag tag, User user) {
        this.title = title;
        this.content = content;
        this.tag = tag;
        this.user = user;
    }

    public enum Tag {
        Backend, Database, Infra;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}