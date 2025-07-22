package com.practice.board_management.dto.post.response;

import com.practice.board_management.domain.post.Post;
import lombok.Getter;

@Getter
public class PersonalPostResponse {

    private String title;
    private String content;

    public PersonalPostResponse(Post post) {
        this.title = post.getTitle();
        this.content = post.getContent();
    }
}
