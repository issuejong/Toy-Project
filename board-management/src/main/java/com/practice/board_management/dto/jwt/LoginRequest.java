package com.practice.board_management.dto.jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor // ← 기본 생성자 추가
public class LoginRequest {
    private String email;
    private String password;
}

