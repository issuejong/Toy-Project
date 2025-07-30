package com.practice.board_management.dto.user.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginResultResponse {
    Long userId;
    String accessToken;
    String refreshToken;
}
