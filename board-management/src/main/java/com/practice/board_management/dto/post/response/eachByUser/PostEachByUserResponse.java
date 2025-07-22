package com.practice.board_management.dto.post.response.eachByUser;

import com.practice.board_management.dto.post.response.PersonalPostResponse;
import lombok.Getter;

import java.util.List;

@Getter
public class PostEachByUserResponse {

    private String nickname;
    private List<PersonalPostResponse> posts;

    public PostEachByUserResponse(String nickname, List<PersonalPostResponse> posts) {
        this.nickname = nickname;
        this.posts = posts;
    }
}
