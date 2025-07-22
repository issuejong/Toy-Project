package com.practice.board_management.dto.user.response;

import com.practice.board_management.domain.users.User;
import lombok.Getter;

@Getter
public class UserResponse {

    private Long userId;
    private String userNickname;
    private String userEmail;
    private User.Role role;

    public UserResponse(User user) {
        this.userId = user.getUserId();
        this.userNickname = user.getNickname();
        this.userEmail = user.getEmail();
        this.role = user.getRole();
    }
}
