package com.practice.board_management.dto.post.response.entireUsers;

import com.practice.board_management.dto.post.response.PersonalPostResponse;
import com.practice.board_management.dto.post.response.eachByUser.PostEachByUserResponse;
import com.practice.board_management.dto.user.response.UserNicknameResponse;
import lombok.Getter;

import java.util.List;

@Getter
public class PostEntireUsersResponse {

    private List<PostEachByUserResponse> responses;

    public PostEntireUsersResponse(List<PostEachByUserResponse> responses) {
        this.responses = responses;
    }
}
