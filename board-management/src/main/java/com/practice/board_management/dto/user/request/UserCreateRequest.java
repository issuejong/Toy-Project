package com.practice.board_management.dto.user.request;

import com.practice.board_management.domain.users.User;
import lombok.Getter;

@Getter
public class UserCreateRequest {

    private Long userId;
    private String password;
    private String nickname;
    private String email;
    private User.Role role;
}
