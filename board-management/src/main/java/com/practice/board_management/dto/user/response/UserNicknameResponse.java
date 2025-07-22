package com.practice.board_management.dto.user.response;

import com.practice.board_management.domain.users.User;
import lombok.Getter;

@Getter
public class UserNicknameResponse {
    private String nickname;

    public UserNicknameResponse(User user) {
        this.nickname = user.getNickname();
    }
}
