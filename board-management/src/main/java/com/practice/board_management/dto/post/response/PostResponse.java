package com.practice.board_management.dto.post.response;

import com.practice.board_management.domain.post.Post;
import lombok.Getter;

@Getter
public class PostResponse {

    private String nickname;
    private String title;
    private String content;
    private int views;

    public PostResponse(Post post) {
        this.nickname = post.getAuthor().getNickname();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.views = post.getViews();
    }
}
