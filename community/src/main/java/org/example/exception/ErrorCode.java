package org.example.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;


@AllArgsConstructor
@Getter
public enum ErrorCode {

    /**
     * 유저 오류
     */
    ALREADY_REGISTERED_USER_EMAIL(HttpStatus.BAD_REQUEST, "U-001", "이미 존재하는 이메일입니다."),
    ALREADY_REGISTERED_USER_NICKNAME(HttpStatus.BAD_REQUEST, "U-002", "이미 존재하는 닉네임입니다."),
    NOT_EXIST_POST(HttpStatus.BAD_REQUEST, "U-003", "게시글이 존재하지 않습니다.");

    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String message;
}
