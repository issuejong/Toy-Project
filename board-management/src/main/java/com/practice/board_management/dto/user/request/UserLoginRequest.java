package com.practice.board_management.dto.user.request;

import lombok.Getter;

@Getter
public class UserLoginRequest {

    private String email;
    private String password;
}
